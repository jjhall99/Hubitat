/**
 *  Hayward AquaConnect Hubitat Child Device Handler
 *
 *  Copyright 2020 Jason Hall
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */

metadata {
    definition (name: "AquaConnect Child", namespace: "jjhall99", author: "Jason Hall", cstHandler: true) {
            capability "Switch"
            capability "Actuator"
            //capability "Polling"
            capability "Refresh"
            //capability "Temperature Measurement"
            //capability "Configuration"
            //capability "Health Check"
            //capability "Indicator"
            
           }

    preferences {
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
	}
}

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

def on(value) {
    parent?.componentOn(this.device)
    log.debug "child On"
}

def off(value) {
    parent?.componentOff(this.device)
    log.debug "child Off"
}

void refresh() {
    parent?.childRefresh(device.deviceNetworkId)
}
