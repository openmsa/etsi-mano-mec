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
package com.ubiqube.etsi.mec.meo.v211.model.lcm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.ubiqube.etsi.mec.meo.v211.model.lcm.KeyValuePairs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * MECHostInformation
 */
@Validated
public class MECHostInformation   {
  @JsonProperty("hostId")
  private KeyValuePairs hostId = null;

  @JsonProperty("hostName")
  private String hostName = null;

  public MECHostInformation hostId(KeyValuePairs hostId) {
    this.hostId = hostId;
    return this;
  }

  /**
   * Get hostId
   * @return hostId
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public KeyValuePairs getHostId() {
    return hostId;
  }

  public void setHostId(KeyValuePairs hostId) {
    this.hostId = hostId;
  }

  public MECHostInformation hostName(String hostName) {
    this.hostName = hostName;
    return this;
  }

  /**
   * Human-readable name of MEC host.
   * @return hostName
  **/
  @ApiModelProperty(value = "Human-readable name of MEC host.")
  
    public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECHostInformation meCHostInformation = (MECHostInformation) o;
    return Objects.equals(this.hostId, meCHostInformation.hostId) &&
        Objects.equals(this.hostName, meCHostInformation.hostName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostId, hostName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECHostInformation {\n");
    
    sb.append("    hostId: ").append(toIndentedString(hostId)).append("\n");
    sb.append("    hostName: ").append(toIndentedString(hostName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
