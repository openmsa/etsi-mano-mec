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
package com.ubiqube.etsi.mec.mepm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mec.lcm.AppInstance;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppLiveInstance;
import com.ubiqube.etsi.mano.dao.mec.tasks.AppComputeTask;
import com.ubiqube.etsi.mano.dao.mec.tasks.AppNetworkTask;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mec.mepm.repositories.AppLiveInstanceJpa;
import com.ubiqube.etsi.mec.repositories.AppInstanceJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class AppInstanceService {

	private final AppInstanceJpa appInstanceJpa;

	private final AppLiveInstanceJpa apoAppLiveInstanceJpa;

	public AppInstanceService(final AppInstanceJpa appInstanceJpa, final AppLiveInstanceJpa apoAppLiveInstanceJpa) {
		super();
		this.appInstanceJpa = appInstanceJpa;
		this.apoAppLiveInstanceJpa = apoAppLiveInstanceJpa;
	}

	public AppInstance findById(final UUID id) {
		return appInstanceJpa.findById(id).orElseThrow(() -> new NotFoundException("Could not find appInstance with id: " + id));
	}

	public boolean isInstantiate(final UUID id) {
		return 0 == appInstanceJpa.countByAppPkgId(id);
	}

	public void delete(final UUID id) {
		appInstanceJpa.deleteById(id);
	}

	public AppInstance save(final AppInstance vnfInstance) {
		return appInstanceJpa.save(vnfInstance);
	}

	public Optional<AppLiveInstance> findLiveInstanceById(final UUID id) {
		return apoAppLiveInstanceJpa.findById(id);
	}

	public void deleteLiveInstanceById(final UUID id) {
		appInstanceJpa.deleteById(id);
	}

	public AppLiveInstance save(final AppLiveInstance vli) {
		return apoAppLiveInstanceJpa.save(vli);

	}

	public List<AppLiveInstance> getLiveVirtualLinkInstanceOf(final AppInstance vnfInstance) {
		return apoAppLiveInstanceJpa.findByAppInstanceAndClass(vnfInstance, AppNetworkTask.class.getSimpleName());
	}

	public List<AppLiveInstance> getLiveComputeInstanceOf(final AppInstance vnfInstance) {
		return apoAppLiveInstanceJpa.findByAppInstanceAndClass(vnfInstance, AppComputeTask.class.getSimpleName());
	}

}
