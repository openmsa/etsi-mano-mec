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
package com.ubiqube.etsi.mano.dao.mec.lcm;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ubiqube.etsi.mano.dao.mano.AuditListener;
import com.ubiqube.etsi.mano.dao.mano.ChangeType;
import com.ubiqube.etsi.mano.dao.mano.ResourceTypeEnum;
import com.ubiqube.etsi.mano.dao.mano.ScaleInfo;
import com.ubiqube.etsi.mano.dao.mano.VimTask;
import com.ubiqube.etsi.mano.dao.mano.v2.AbstractTask;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "mec_meo")
@EntityListeners(AuditListener.class)
public class AppTask extends AbstractTask implements VimTask {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String vimReservationId;

	@Enumerated(EnumType.STRING)
	private ChangeType changeType;

	@Enumerated(EnumType.STRING)
	private ResourceTypeEnum type;

	private String zoneId;

	private String resourceGroupId;

	@ManyToOne
	private AppBlueprint blueprint;

	private UUID removedVnfLiveInstance;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ScaleInfo scaleInfo;

	private String resourceProviderId;

	private String vimConnectionId;

	@Override
	public UUID getRemovedLiveInstance() {
		return removedVnfLiveInstance;
	}

}
