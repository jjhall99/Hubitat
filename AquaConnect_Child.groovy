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
            capability "Relay Switch"
            capability "Refresh"
            capability "Actuator"
            capability "Sensor"
                        
            attribute "status", "String"
            
            //command "logsOff"
            command "logsOn"
            command "on"
            command "off"
            command "refresh"            
           }

    preferences {
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
    	}
}

def installed () {
    sendEvent(name: "switch", value:"off", descriptionText:"set initial switch value")
    log.debug "child initialize run"
}

def updated () {
    unschedule()
    if (debugOutput) runIn(1800,logsOff)
}

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("debugOutput",[value:"false",type:"bool"])
}

private logDebug(msg) {
    if (settings?.debugOutput || settings?.debugOutput == null) {
        log.debug "$msg"
    }
}

void on() {
    def name = device.label.split(" ")[-1]
    parent?.componentOff(name)
    //parent?.componentOn(this.device)
    //sendEvent(name: "switch", value:"on", isStateChange: true)
    logDebug "child On"
}

void off() {
    def name = device.label.split(" ")[-1]
    parent?.componentOff(name)
    //parent?.componentOff(this.device)
    //sendEvent(name: "switch", value:"off", isStateChange: true)
    logDebug "child Off"
}

void refresh() {
    parent?.refresh()
    logDebug "child Refresh"
}
