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
package com.ubiqube.etsi.mano.dao.mec.pkg;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@Getter
@Setter
@Entity
@Table(schema = "mec_meo")
public class TrafficFilter implements Serializable {
	/** Serial. */
	private static final long serialVersionUID = 1L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private Integer dSCP;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> dstAddress;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> dstPort;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> dstTunnelPort;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> protocol;

	private Integer qCI;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> srcAddress;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> srcPort;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> srcTunnelAddress;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> srcTunnelPort;

	private Integer tC;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> tag;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(schema = "mec_meo")
	private Set<String> tgtTunnelAddress;

}
