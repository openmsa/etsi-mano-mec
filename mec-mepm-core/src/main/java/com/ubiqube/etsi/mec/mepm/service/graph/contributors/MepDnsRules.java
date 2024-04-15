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
package com.ubiqube.etsi.mec.mepm.service.graph.contributors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.v2.PlanStatusType;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppBlueprint;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppTask;
import com.ubiqube.etsi.mano.dao.mec.pkg.AppPkg;
import com.ubiqube.etsi.mano.dao.mec.pkg.DNSRuleDescriptor;
import com.ubiqube.etsi.mano.orchestrator.nodes.Node;
import com.ubiqube.etsi.mano.orchestrator.nodes.mec.MepDnsRulesNode;
import com.ubiqube.etsi.mano.orchestrator.nodes.vnfm.Compute;
import com.ubiqube.etsi.mano.service.graph.vnfm.UnitOfWork;
import com.ubiqube.etsi.mano.service.graph.wfe2.DependencyBuilder;
import com.ubiqube.etsi.mec.mepm.service.graph.AbstractAppPlanContributor;
import com.ubiqube.etsi.mec.mepm.service.graph.AppParameters;
import com.ubiqube.etsi.mec.mepm.service.graph.mepm.MepDnsRulesTask;
import com.ubiqube.etsi.mec.mepm.service.graph.uow.AppMepDnsUow;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class MepDnsRules extends AbstractAppPlanContributor {

	@Override
	public Class<? extends Node> getContributionType() {
		return MepDnsRulesNode.class;
	}

	@Override
	public List<AppTask> contribute(final AppPkg bundle, final AppBlueprint blueprint, final Set<ScaleInfo> scaling) {
		final Set<DNSRuleDescriptor> rules = bundle.getAppDNSRule();
		if (rules.isEmpty()) {
			return new ArrayList<>();
		}
		final MepDnsRulesTask task = new MepDnsRulesTask();
		task.setAlias(bundle.getAppName());
		task.setAppdPkg(bundle);
		task.setBlueprint(blueprint);
		task.setChangeType(ChangeType.ADDED);
		task.setStatus(PlanStatusType.NOT_STARTED);
		task.setToscaName(bundle.getAppName());
		task.setType(ResourceTypeEnum.DNSZONE);
		return Arrays.asList(task);
	}

	@Override
	public List<UnitOfWork<AppTask, AppParameters>> convertTasksToExecNode(final Set<AppTask> tasks, final AppBlueprint blueprint) {
		return tasks.stream()
				.filter(MepDnsRulesTask.class::isInstance)
				.map(MepDnsRulesTask.class::cast)
				.map(AppMepDnsUow::new).collect(Collectors.toList());
	}

	@Override
	public void getDependencies(final DependencyBuilder dependencyBuilder) {
		dependencyBuilder.connectionFrom(Compute.class);

	}

}
