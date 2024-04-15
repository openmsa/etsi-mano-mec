/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mec.mepm.service.graph;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.ListenableGraph;
import org.springframework.stereotype.Service;

import com.github.dexecutor.core.task.ExecutionResults;
import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.v2.Blueprint;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppBlueprint;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppTask;
import com.ubiqube.etsi.mano.dao.mec.pkg.AppPkg;
import com.ubiqube.etsi.mano.orchestrator.OrchExecutionResults;
import com.ubiqube.etsi.mano.orchestrator.PreExecutionGraph;
import com.ubiqube.etsi.mano.orchestrator.nodes.ConnectivityEdge;
import com.ubiqube.etsi.mano.orchestrator.nodes.NodeConnectivity;
import com.ubiqube.etsi.mano.service.event.Workflow;
import com.ubiqube.etsi.mano.service.graph.GenericExecParams;
import com.ubiqube.etsi.mano.service.graph.GraphTools;
import com.ubiqube.etsi.mano.service.graph.vnfm.UnitOfWork;
import com.ubiqube.etsi.mano.service.graph.wfe2.WfConfiguration;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class AppWorkflow implements Workflow<AppPkg, AppBlueprint, AppReport, AppTask> {
	private final AppPlanner planner;

	private final AppPlanExecutor executor;

	private final List<AbstractAppPlanContributor> planContributors;

	public AppWorkflow(final AppPlanner planner, final AppPlanExecutor executor, final List<AbstractAppPlanContributor> planContributors) {
		this.planner = planner;
		this.executor = executor;
		this.planContributors = new ArrayList<>(planContributors);
	}

	@Override
	public PreExecutionGraph<AppTask> setWorkflowBlueprint(final AppPkg bundle, final AppBlueprint blueprint) {
		final WfConfiguration wfc = new WfConfiguration(planContributors);
		final List<NodeConnectivity> conns = wfc.getConfigurationGraph().edgeSet().stream().toList();
		planner.doPlan(bundle, blueprint, null, conns);
		return null;
	}

	@Override
	public AppReport execCreate(final AppBlueprint plan, final GenericExecParams params) {
		final ListenableGraph<UnitOfWork<AppTask, AppParameters>, ConnectivityEdge<UnitOfWork<AppTask, AppParameters>>> createPlan = planner.convertToExecution(plan, ChangeType.ADDED);
		GraphTools.exportGraph(createPlan, "app-added.dot");
		final ExecutionResults<UnitOfWork<AppTask, AppParameters>, String> createResults = executor.execCreate(createPlan, () -> new UowTaskCreateProvider<>((AppParameters) params));
		return new AppReport(createResults);
	}

	@Override
	public AppReport execDelete(final AppBlueprint blueprint, final GenericExecParams params) {
		final ListenableGraph<UnitOfWork<AppTask, AppParameters>, ConnectivityEdge<UnitOfWork<AppTask, AppParameters>>> graph = planner.convertToExecution(blueprint, ChangeType.REMOVED);
		GraphTools.exportGraph(graph, "app-del.dot");
		final ExecutionResults<UnitOfWork<AppTask, AppParameters>, String> removeResults = executor.execDelete(graph, () -> new UowTaskDeleteProvider<>((AppParameters) params));
		return new AppReport(removeResults);
	}

	@Override
	public void refresh(final PreExecutionGraph<AppTask> prePlan, final Blueprint<AppTask, ?> localPlan) {
		// TODO Auto-generated method stub

	}

	@Override
	public OrchExecutionResults<AppTask> execute(final PreExecutionGraph<AppTask> plan, final AppBlueprint parameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
