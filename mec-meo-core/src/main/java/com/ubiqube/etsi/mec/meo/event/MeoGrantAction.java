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
package com.ubiqube.etsi.mec.meo.event;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.dao.mano.GrantResponse;
import com.ubiqube.etsi.mano.dao.mano.SoftwareImage;
import com.ubiqube.etsi.mano.dao.mano.VnfCompute;
import com.ubiqube.etsi.mano.dao.mano.VnfStorage;
import com.ubiqube.etsi.mano.dao.mec.lcm.AppInstance;
import com.ubiqube.etsi.mano.exception.NotFoundException;
import com.ubiqube.etsi.mano.jpa.GrantsResponseJpa;
import com.ubiqube.etsi.mano.service.event.AbstractGrantAction;
import com.ubiqube.etsi.mano.service.event.elect.VimElection;
import com.ubiqube.etsi.mano.service.vim.VimManager;
import com.ubiqube.etsi.mec.repositories.AppInstanceJpa;
import com.ubiqube.etsi.mec.repositories.AppPkgJpa;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Service
public class MeoGrantAction extends AbstractGrantAction {

	private final GrantsResponseJpa grantJpa;

	private final AppInstanceJpa appInstanceJpa;

	private final AppPkgJpa appPkgJpa;

	protected MeoGrantAction(final GrantsResponseJpa grantJpa, final VimManager vimManager, final VimElection vimElection, final AppInstanceJpa appInstanceJpa,
			final AppPkgJpa appPkgJpa) {
		super(grantJpa, vimManager, vimElection);
		this.grantJpa = grantJpa;
		this.appInstanceJpa = appInstanceJpa;
		this.appPkgJpa = appPkgJpa;
	}

	@Override
	protected Set<VnfCompute> getVnfCompute(final UUID id) {
		final GrantResponse grant = grantJpa.findById(id).orElseThrow();
		final AppInstance appInstance = appInstanceJpa.findById(UUID.fromString(grant.getVnfInstanceId())).orElseThrow(() -> new NotFoundException("Could not find App Instance: " + grant.getVnfInstanceId()));
		return Collections.singleton(appInstance.getAppPkg().getVirtualComputeDescriptor());
	}

	@Override
	protected Set<VnfStorage> getVnfStorage(final UUID id) {
		// XXX No storage in specs?
		return new HashSet<>();
	}

	@Override
	protected InputStream findImage(final SoftwareImage softwareImage, final String vnfdId) {
		// TODO Auto-generated method stub
		return null;
	}

}
