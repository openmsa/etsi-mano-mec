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

import java.util.List;

import com.github.dexecutor.core.task.ExecutionResult;
import com.github.dexecutor.core.task.ExecutionResults;
import com.ubiqube.etsi.mano.dao.mano.v2.Task;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppTask;
import com.ubiqube.etsi.mano.service.event.Report;
import com.ubiqube.etsi.mano.service.event.ReportItem;
import com.ubiqube.etsi.mano.service.graph.vnfm.UnitOfWork;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class AppReport implements Report {
	private final ExecutionResults<UnitOfWork<AppTask, AppParameters>, String> results;

	public AppReport(final ExecutionResults<UnitOfWork<AppTask, AppParameters>, String> results) {
		this.results = results;
	}

	public List<ExecutionResult<UnitOfWork<AppTask, AppParameters>, String>> getSkipped() {
		return results.getSkipped();
	}

	@Override
	public List<ReportItem> getSuccess() {
		return results.getSuccess().stream().map(this::map).toList();
	}

	@Override
	public List<ReportItem> getErrored() {
		return results.getErrored().stream().map(this::map).toList();
	}

	public List<ExecutionResult<UnitOfWork<AppTask, AppParameters>, String>> getAll() {
		return results.getAll();
	}

	private ReportItem map(final ExecutionResult<UnitOfWork<AppTask, AppParameters>, String> er) {
		final Task part = er.getId().getTaskEntity();
		return new ReportItem(part);
	}

}
