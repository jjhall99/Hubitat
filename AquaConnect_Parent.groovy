/**
 *  Hayward AquaConnect SmartThings Parent Device Handler
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

import groovy.json.JsonSlurper
import groovy.transform.Field
@Field static commandsMap = [Lights:"09", Heater:"13", Blower:"0A", Spa:"07", Filter:"08"]

metadata {
    definition (name: "AquaConnect Parent", namespace: "jjhall99", author: "Jason Hall", cstHandler: true) {
            capability "Switch"
            capability "Actuator"
            capability "Polling"
            capability "Refresh"
            capability "Temperature Measurement"
            capability "Configuration"
            capability "Health Check"
            capability "Indicator"
            
            attribute "displayLine1", "String"
            attribute "displayLine2", "String"
            attribute "airTemp", "Number"
            attribute "poolTemp", "Number"
            attribute "spaTemp", "Number"
            attribute "saltLevel", "Number"
            attribute "chlorinatorStatus", "Number"
            attribute "filter", "String"
            attribute "heater", "String"
            attribute "blower", "String"
            //attribute "switch", "String" 
            attribute "lights", "String"
            attribute "currentMode", "String"
            attribute "alertFlag", "String"
            
            command "componentOn(String)";
            command "componentOff(String)";
            //command "toggleLightMode(count)";
            command "filter"
            command "blower"
            //command "changeMode"
            command "heater"
            command "toggleLights"
            
            command "createChildDevices"
            command "recreateChildDevices"
            command "deleteChildren"
            }
    
    simulator {
		// TODO: define status and reply messages here
        }

    /*
    tiles (scale:2){
        /*pool (mode, air temp, pool temp, spa temp, salt level, chlorinatorStatus, refresh), 
         * filter, 
         * lights (switch, toggle, choose), 
         * spa, 
         * heater, 
         * blower, 
         * superChlor
        standardTile("currentMode", "currentMode", width: 2, height: 1, decoration: "flat") {
                    //state "OFF", label: "OFF Mode", action: "changeMode", backgroundColor: "#ffffff"//, icon: "st.Health & Wellness.health2"
                    state "Spa", label: "Spa Mode", action: "changeMode", backgroundColor: "#ffffff"//, icon: "st.Health & Wellness.health2"
                    state "Pool", label: "Pool Mode", action: "changeMode", backgroundColor: "#ffffff"//, icon: "st.Health & Wellness.health2"
        }
        valueTile("airTemp", "airTemp", width: 2, height: 1, decoration: "flat") {
                    state ("temperature", label: 'Air Temp:\n${currentValue}°F',unit:"dF", backgroundColor: "#ffffff")
        }
        valueTile("poolTemp", "poolTemp", width: 2, height: 1, decoration: "flat") {
                    state ( "temperature", label: 'Pool Temp:\n${currentValue}°F', unit:"dF", backgroundColors:[
                      [value: 77, color: "#153591"], //dark blue
                      [value: 78, color: "#00a0dc"], //standard blue
                      //[value: 59, color: "#90d2a7"], //light green
                      //[value: 84, color: "#44b621"], //green
                      [value: 88, color: "#f1d801"], //yellow
                      //[value: 95, color: "#d04e00"], //orange
                      //[value: 96, color: "#bc2323"]  //red
                  ]
                )
        }
        valueTile("spaTemp", "spaTemp", width: 2, height: 1, decoration: "flat") {
                    //state "temperature", label: 'Spa Temp:\n${currentValue}', unit:dF, backgroundColor: "#ffffff"
                    state ( "temperature", label: 'Spa Temp:\n${currentValue}°F', unit:"dF", backgroundColors:[
                      [value: 77, color: "#153591"], //dark blue
                      [value: 78, color: "#00a0dc"], //standard blue
                      //[value: 59, color: "#90d2a7"], //light green
                      //[value: 84, color: "#44b621"], //green
                      [value: 88, color: "#f1d801"], //yellow
                      [value: 95, color: "#d04e00"], //orange
                      [value: 100, color: "#bc2323"]  //red
                  ]
                )
        } 
        valueTile("saltLevel", "saltLevel", width: 2, height: 1, decoration: "flat") {
                    //state "level", label: '${currentValue}',backgroundColor: "#ffffff"
                    state ( "level", label: 'Salt Level: \n${currentValue} PPM', unit: "PPM", backgroundColors:[
                    [value: 2600, color: "#bc2323"],  //red
                    [value: 2800, color: "#f1d801"], //yellow
                    [value: 3000, color: "#ffffff"] //standard grey backround                  
                  ]
                )
        }
        valueTile("chlorinatorStatus", "chlorinatorStatus", width: 2, height: 1, decoration: "flat") {
                    state "level", label: 'Chlorinator Level:\n${currentValue}%',unit:"%", backgroundColor: "#ffffff"
        }
        valueTile("refresh", "device.refresh", decoration: "flat", width: 1, height: 1) {
                    state "default", label: 'Refresh', action:"refresh", icon:"st.secondary.refresh"
        }
        valueTile ("dummyTile1", "dummyTile1", width:1, height:1, decoration: "flat") {
            state ("default", label: " ", backgroundColor: "#ffffff")
        }
        valueTile ("dummyTile2", "dummyTile2", width:2, height:2, decoration: "flat") {
            state ("default", label: " ", backgroundColor: "#ffffff")
        }
        valueTile("line1", "displayLine1", width: 3, height: 1, decoration: "flat") {
                    state "default", label: '${currentValue}', action:"refresh", backgroundColor: "#ffffff"
            } 
        valueTile("line2", "displayLine2", width: 3, height: 1, decoration: "flat") {
                    state "default", label: '${currentValue}', action: "refresh", backgroundColor: "#ffffff"
            }
        standardTile("filter", "filter", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
                    state "off", label: 'Filter ${name}', action: "filter", icon: "st.Health & Wellness.health2", backgroundColor: "#ff0000", nextState: on
                    state "on", label: 'Filter ${name}', action: "filter", icon: "st.Health & Wellness.health2", backgroundColor: "#00a0dc", nextState: off
        }
        standardTile("heater", "heater", width: 2, height: 2, canChangeIcon: true, decoration: "flat") {
                    state "off", label: 'Heater ${name}', action: "heater", icon: "st.Weather.weather2", backgroundColor: "#ffffff"
                    state "on", label: 'Heater ${name}', action: "heater", icon: "st.Weather.weather2", backgroundColor: "#ff0000" 
                    //state "auto control", label: '${name}', icon: "st.Weather.weather2", backgroundColor: "#ffffff"
        }
        standardTile("blower", "blower", width: 2, height: 2, decoration: "flat") {
                    state "off", label: 'Blower ${name}', action: "blower", icon: "st.Appliances.appliances11", backgroundColor: "#ffffff"
                    state "on", label: 'Blower ${name}', action: "blower", icon: "st.Appliances.appliances11", backgroundColor: "#ff0000" 
        }
        standardTile("lights", "device.switch", width: 2, height: 2, decoration: "flat") {
                    state "off", label: 'Lights ${name}', action: "on", icon: "st.Appliances.appliances11", backgroundColor: "#ffffff", nextState: on
                    state "on", label: 'Lights ${name}', action: "off", icon: "st.Appliances.appliances11", backgroundColor: "#00a0dc", nextState: off
        }
        valueTile("toggleLights", "toggleLights", width: 2, height: 2, decoration: "flat") {
                    state "default", label: 'Toggle Lights', action:"toggleLights", icon:"st.secondary.refresh"
        }
    }    
        
      //not in use
        childDeviceTile("lights", "device.switch", width: 2, height: 2,  canChangeIcon: true, decoration: "flat", childTileName: lights)
        childDeviceTile("filter", "device.switch", width: 2, height: 2,  canChangeIcon: true, decoration: "flat", childTileName: filter)
        childDeviceTile("toggleLights", "toggleLights", width: 2, height: 2, canChangeIcon: true, decoration: "flat", childTileName: toggleLights) 
        childDeviceTile("changeLights", "chooseLights", width: 2, height: 2, canChangeIcon: true,decoration: "flat", childTileName: chooseLights) 
        childDeviceTile("heater", "device.switch", width: 2, height: 2,  canChangeIcon: true, decoration: "flat", childTileName: heater)
        childDeviceTile("blower", "device.switch", width: 2, height: 2,  canChangeIcon: true, decoration: "flat", childTileName: blower)
        childDeviceTile("superChlor", "device.switch", width: 2, height: 2, canChangeIcon: true,decoration: "flat", childTileName: superChlor)
        
        main "lights"
        details (["airTemp", "poolTemp", "spaTemp", "currentMode", "saltLevel", "chlorinatorStatus", "line1", "line2", "filter", "heater", "blower", "lights", "toggleLights", "dummyTile2"])
    */
    }   
    
    
def installed() {
    createChildDevices()
    initialize()
    log.debug "Parent Installed"
}    
def createChildDevices() {
    log.debug "Parent createChildDevices"    
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.08", [name:"AquaConnect Child Device", label: "Filter", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.09", [name:"AquaConnect Child Device", label: "Lights", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.07", [name:"AquaConnect Child Device", label: "Spa", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.13", [name:"AquaConnect Child Device", label: "Heater", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.0A", [name:"AquaConnect Child Device", label: "Blower", isComponent:true])   
        //addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.6", [name:"$device.displayName child.6", label: "superChlor", isComponent:true])    
    }
    
def recreateChildDevices() {
    log.debug "Parent recreateChildDevices"
    deleteChildren()
    createChildDevices()
}

def deleteChildren() {
	log.debug "Parent deleteChildren"
	def children = getChildDevices()
    
    children.each {child->
  		deleteChildDevice(child.deviceNetworkId)
    }
}

def updated() {
    initialize()
    installed()
    log.info ("updated run")
}

def initialize() {
    //response(refresh())
    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
    sendEvent(name: "healthStatus", value: "online")
    //sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
    sendEvent(name: "poolTemp", value: "50", isStateChange: true)
    sendEvent(name: "spaTemp", value: "50", isStateChange: true)
    sendEvent(name: "airTemp", value: "50", isStateChange: true)
    sendEvent(name: "saltLevel", value: "1000", isStateChange: true)
    sendEvent(name: "chlorinatorStatus", value: "65", isStateChange: true)
    log.info ("initialize run")
}    

def parse(String description) {
	//log.debug "Parsing '${description}'"
     
        def message = parseLanMessage(description)
        //log.debug(message)
        
        def responseLines = message.body.substring(message.body.indexOf("<body>")+6, message.body.indexOf("</body>")).tokenize('\n')
        responseLines = responseLines.drop(1)
        responseLines = responseLines.take(3)

        def line1 = responseLines[0].substring(0, responseLines[0].length()-1)
        line1 = line1.substring(0, line1.length()-3)
        line1 = line1.replaceAll("&#176", "°")
        line1 = line1.replaceAll("[\u0000-\u001f]", "")
        line1 = line1.replaceAll("&nbsp;", " ")
        line1 = line1.replaceAll('<span class="WBON">Spd1</span>', "")
        
        while(line1.startsWith(" ")) line1 = line1.substring(1)
        log.debug(line1)
        sendEvent(name: "displayLine1", value: line1, isStateChange: true)

        def line2 = responseLines[1].substring(0, responseLines[1].length()-1)
        line2 = line2.substring(0, line2.length()-3)
        line2 = line2.replaceAll("&#176", "°")
        line2 = line2.replaceAll("[\u0000-\u001f]", "")
        line2 = line2.replaceAll("&nbsp;", " ")
        line2 = line2.replaceAll("Turned ", "")
        line2 = line2.replaceAll("Speed1", "")
        //line2 = line2.replaceAll("%", "")
    
        while(line2.startsWith(" ")) {line2 = line2.substring(1)}
        log.debug(line2) 
        sendEvent(name: "displayLine2", value: line2, isStateChange: true)

        def line3 = responseLines[2].substring(0, responseLines[2].length()-1)
        line3 = line3.substring(0, line3.length()-3)
        log.debug(line3)

    //Parse Pool Temp
        if(line1.contains("Pool Temp")) {
            line1 = line1.replaceAll("Pool Temp ", "")
            line1 = line1.replaceAll("°F", "")
            int con = line1 as Integer
            int poolTemp = device.currentValue("poolTemp")
            /*log.debug ("device.currentValue(pooltemp) is ${poolTemp}")
            if (poolTemp == con){
                //log.debug("Pool Temp unchanged")
                sendEvent(name: "poolTemp", value: con, isStateChange: false)
            }else {
                log.debug("Pool Temp changed from ${poolTemp} to ${con}")
                sendEvent(name: "poolTemp", value: con, isStateChange: true)
            }*/
            if (poolTemp == con){
                //log.debug("Pool Temp unchanged")
                sendEvent(name: "poolTemp", value: con, isStateChange: false)
            }else {
                log.debug("Pool Temp changed from ${poolTemp} to ${con}")
                sendEvent(name: "poolTemp", value: con, isStateChange: true)
            }
        
    //If Pool Mode, Sync Spa Temp
            if(getSecondLedStatus(line3.charAt(0)) == "on")/*Pool Mode*/{
                int spaTemp = device.currentValue("spaTemp") 
                //int spaTemp = 100
                if (spaTemp == con) {sendEvent(name: "spaTemp", value: con, isStateChange: false)
                }else {sendEvent(name: "spaTemp", value: con, isStateChange: true)
                log.debug("Spa Temp changed from ${spaTemp} to ${con}")
                }
            }
        }

    //parse Spa Temp
        if(line1.contains("Spa Temp")) {
            line1 = line1.replaceAll("Spa Temp ", "")
            line1 = line1.replaceAll("°F", "")
            //log.debug("Spa temp updated")
            int con = line1 as Integer
            //sendEvent(name: "spaTemp", value: con, isStateChange: true)
            //log.debug ("con is ${con}")
            int spaTemp = device.currentValue("spaTemp")
            //log.debug ("device.currentValue(spatemp) is ${spaTemp}")
            if (spaTemp == con){
                //log.debug("Spa Temp unchanged")
                sendEvent(name: "spaTemp", value: con, isStateChange: false)
            }else {
                log.debug("Spa Temp changed from ${spaTemp} to ${con}")
                sendEvent(name: "spaTemp", value: con, isStateChange: true)
            }
        }
        
    //parse Air Temp    
        if(line1.contains("Air Temp")) {
            line1 = line1.replaceAll("Air Temp ", "")
            line1 = line1.replaceAll("°F", "")
            //log.debug("Air temp updated")
            int con = line1 as Integer
            //sendEvent(name: "airTemp", value: con, isStateChange: true)
            int airTemp = device.currentValue("airTemp")
            //log.debug ("device.currentValue(airtemp) is ${airTemp}")
            if (airTemp == con){
                //log.debug("Air Temp unchanged")
                sendEvent(name: "airTemp", value: con, isStateChange: false)
            }else {
                log.debug("Air Temp changed from ${airTemp} to ${con}")
                sendEvent(name: "airTemp", value: con, isStateChange: true)
            }
        }
        
    //parse Chlorinator Status    
        if(line1.contains("Chlorinator")) {
            line2 = line2.replaceAll("%", "")
            //log.debug("Pool Chlorinator status");
            int con = line2 as Integer
            //sendEvent(name: "chlorinatorStatus", value: con, isStateChange: true)
            int chlorinatorStatus = device.currentValue("chlorinatorStatus")
            //log.debug ("device.currentValue(chlorinatorStatus) is ${chlorinatorStatus}")
            if (chlorinatorStatus == con){
                //log.debug("Chlorinator Status unchanged")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: false)
            }else {
                log.debug("Chlorinator Status changed from ${chlorinatorStatus}% to ${con}%")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: true)
            }
        } 
        
    //update chlorinator status for super chlorinate    
        if(line1.contains("Super Chlorinate")) {
            //log.debug("Super Chlorinator On, updating pool chlorinator status");
            //sendEvent(name: "chlorinatorStatus", value: 100, isStateChange: true)
            int con = 100
            int chlorinatorStatus = device.currentValue("chlorinatorStatus")
            //log.debug ("device.currentValue(spatemp) is ${spaTemp}")
            if (chlorinatorStatus == con){
                //log.debug("Chlorinator Status unchanged")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: false)
            }else {
                log.debug("Chlorinator Status changed")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: true)
            }
        }

        if(line1.contains("Salt Level")) {
            //line1 = line1.replaceAll("Salt Level ", "")
            line2 = line2.replaceAll(" PPM", "")
            //log.debug("Salt Level updated")
            int con = line2 as Integer
            //sendEvent(name: "saltLevel", value: con, isStateChange: true)
            //sendEvent(name: "saltLevel", value: line1, isStateChange: true)
            int saltLevel = device.currentValue("saltLevel") as Integer
            //log.debug ("device.currentValue(saltLevel) is ${saltLevel}")
            if (saltLevel == con){
                //log.debug("Salt Level unchanged")
                sendEvent(name: "saltLevel", value: con, isStateChange: false)
            }else {
                log.debug("Salt Level changed from ${saltLevel} to ${con}")
                sendEvent(name: "saltLevel", value: con, isStateChange: true)
            }
        }
        
        if(line1.contains("Check System")){
            sendEvent(name: "alertFlag", value: "${line2}", isStateChange: true)
            //def sayThis = '${line1}, ${line2}'
            //sendNotification("test message",[method:"push"])  //sendNotification("test notification - push", [method: "push"])
            //log.debug("${sayThis}")
        }
        
        def currentMode = device.currentValue("currentMode")
        //log.debug("Current Mode is ${currentMode}")
        //log.debug("getSecondLedStatus(line3.charAt(0)) == ${getSecondLedStatus(line3.charAt(0))}")
        if(getSecondLedStatus(line3.charAt(0)) == "on")/*Pool Mode*/{
            //log.debug("getFirstLedStatus(line3.charAt(0)) is off which should mean pool mode")
            if(currentMode=="Spa") {
                sendEvent(name: "currentMode", value: "Pool", isStateChange: true)
                log.debug("The Pool is now in Pool Mode")
                }else {sendEvent(name: "currentMode", value: "Pool", isStateChange: false)
                    //log.debug("Current Mode unchanged (Pool)")
                }
        } else { if(currentMode=="Spa") { 
            sendEvent(name: "currentMode", value: "Spa", isStateChange: false)
            //log.debug("Current Mode unchanged (Spa)")
            } else {sendEvent(name: "currentMode", value: "Spa", isStateChange: true)
            log.debug("The Pool is now in Spa Mode")}
        }
        
    //parse lights status                        
        /*def status = device.currentValue("switch")
        if(getSecondLedStatus(line3.charAt(2))=="on"){
            if(status=="off") {
                sendEvent(name: "switch", value: "on", isStateChange: true)
                log.debug("Pool Lights are now on")
                }else {sendEvent(name: "switch", value: "on", isStateChange: false)
                    //log.debug("Lights are on (unchanged)")
                }
        } else { if(status=="off"){
            sendEvent(name: "switch", value: "off", isStateChange: false)
            //log.debug("Lights are off (unchanged)")
            } else {sendEvent(name: "switch", value: "off", isStateChange: true)
            log.debug("Pool Lights are now off")}
        } */
        
        def status = device.currentValue("lights")
        if(getSecondLedStatus(line3.charAt(2))=="on"){
            if(status=="off") {
                sendEvent(name: "lights", value: "on", isStateChange: true)
                log.debug("Pool Lights are now on")
                }else {sendEvent(name: "lights", value: "on", isStateChange: false)
                    //log.debug("Lights are on (unchanged)")
                }
        } else { if(status=="off"){
            sendEvent(name: "lights", value: "off", isStateChange: false)
            //log.debug("Lights are off (unchanged)")
            } else {sendEvent(name: "lights", value: "off", isStateChange: true)
            log.debug("Pool Lights are now off")}
        } 
    
    //parse filter status
        def filter = device.currentValue("filter")
        if(getFirstLedStatus(line3.charAt(2))=="on")/*filter on*/{
            if(filter=="off") {
                sendEvent(name: "filter", value: "on", isStateChange: true)
                log.debug("Pool Filter is now on")
                }else {sendEvent(name: "filter", value: "on", isStateChange: false)
                    //log.debug("Filter is on (unchanged)")
                }
        } else { if(filter=="off"){
            sendEvent(name: "filter", value: "off", isStateChange: false)
            //log.debug("Filter is off (unchanged)")
            } else {sendEvent(name: "filter", value: "off", isStateChange: true)
            log.debug("Pool Filter is now off")}
        }
    //parse heater status
        //if(getFirstLedStatus(line3.charAt(3))=="on")/*heater on*/{
        //    sendEvent(name: "heaterStatus", value: "on", isStateChange: true) 
        //}else sendEvent(name: "heaterStatus", value: "off", isStateChange: true)
    def heater = device.currentValue("heater")
        if(getFirstLedStatus(line3.charAt(3))=="on")/*heater on*/{
            if(heater=="off") {
                sendEvent(name: "heater", value: "on", isStateChange: true)
                log.debug("Pool Heater is now on")
                }else {sendEvent(name: "heater", value: "on", isStateChange: false)
                    //log.debug("Heater is on (unchanged)")
                }
        } else { if(heater=="off"){
            sendEvent(name: "heater", value: "off", isStateChange: false)
            //log.debug("Heater is off (unchanged)")
            } else {sendEvent(name: "heater", value: "off", isStateChange: true)
            log.debug("Pool Heater is now off")}
        }
    
    //parse blower status
        //if(getSecondLedStatus(line3.charAt(4))=="on"){
        //    sendEvent(name: "aux1Status", value: "on", isStateChange: true) 
        //}else sendEvent(name: "aux1Status", value: "off", isStateChange: true)
        def blower = device.currentValue("blower")
        if(getSecondLedStatus(line3.charAt(4))=="on")/*blower on*/{
            if(blower=="off") {
                sendEvent(name: "blower", value: "on", isStateChange: true)
                log.debug("Blower is now on")
                }else {sendEvent(name: "blower", value: "on", isStateChange: false)
                    //log.debug("Blower is on (unchanged)")
                }
        } else { if(blower=="off"){
            sendEvent(name: "blower", value: "off", isStateChange: false)
            //log.debug("Blower is off (unchanged)")
            } else {sendEvent(name: "blower", value: "off", isStateChange: true)
            log.debug("Blower is now off")}
        }
    
        //sendEvent(name: "lights.currentState", value: getSecondLedStatus(line3.charAt(2)), isStateChange: true)
        //sendEvent(name: "filter", value: getFirstLedStatus(line3.charAt(2)), isStateChange: true)  //verified
        //sendEvent(name: "heater", value: getFirstLedStatus(line3.charAt(3)), isStateChange: true)  //verified
        //sendEvent(name: "blower", value: getSecondLedStatus(line3.charAt(4)), isStateChange: true)  //blower verified
        //sendEvent(name: "aux2Status", value: getFirstLedStatus(line3.charAt(5)), isStateChange: true)  //not using

}

def on() {
    //log.debug "Executing On for switch"
    return postKey("WNewSt.htm", "09");
}

def off() {
    //log.debug "Executing Off for switch"
    return postKey("WNewSt.htm", "09");   
}

def toggleLights(){
    int wait = 2*1 as Integer
    log.debug "Executing light toggle Off for ${wait}"
    off()
    runIn (wait, on(), [overwrite:true])
    //pause(wait)
    log.debug "Executing light toggle On for ${wait}"
}
def heater() {
	//sendEvent(name: "heater", value: "on", isStateChange: true)
	return postKey("WNewSt.htm", "13");
}

def filter() {
	//sendEvent(name: "filter", value: "on", isStateChange: true)
	return postKey("WNewSt.htm", "08");
}

def blower() {
	//sendEvent(name: "blower", value: "on", isStateChange: true)
	return postKey("WNewSt.htm", "0A");
}

def changeMode() {
	//sendEvent(name: "currentMode", value: "off", isStateChange: true)
	return postKey("WNewSt.htm", "07");
}
/*
private componentOn(device) {
    log.debug "Executing On ${device}"
    //def num = ""
    def num = "" as String
    switch ($device) {
        case "Lights":
            num="09"
            break
        case "Heater":
            num="13"
            break
        case "Blower":
            num="0A"
            break
        case "Spa":
            num="07"
            break
        case "Filter":
            num="08"
            break    
        default:
            num = "default"
   }
   log.debug("num: ${num}")
   //return postKey("WNewSt.htm", "${num}");
   return postKey("WNewSt.htm", num);
}


private componentOff(device) {
    log.debug "Executing Off ${device}"
    //def num = ""
    def num = "" as String
    switch (device) {
        case "Lights":
            num="09"
            break
        case "Heater":
            num="13"
            break
        case "Blower":
            num="0A"
            break
        case "Spa":
            num="07"
            break
        case "Filter":
            num="08"
            break    
        default:
            num = "default"
   }
   log.debug("num: ${num}")
   //return postKey("WNewSt.htm", "${num}");
   return postKey("WNewSt.htm", num);
} */

def poll() {
    log.debug("polled")
    refresh()
}

def componentOn(device) {
    log.debug "Executing $device On"
    num = commandsMap."$device" ?: "default"
    log.debug "num: $num"
   //return postKey("WNewSt.htm", "${num}");
   return postKey("WNewSt.htm", num);
}


def componentOff(device) {
    log.debug "Executing $device Off"
    num = commandsMap."$device" ?: "default"
    log.debug "num: $num"
   //return postKey("WNewSt.htm", "${num}");
   return postKey("WNewSt.htm", num);
}

                    
def refresh() {
    return createGetRequest("WNewSt.htm");
}

private getCallBackAddress() {
    return device.hub.getDataValue("localIP") + ":" + device.hub.getDataValue("localSrvPortTCP")
}

private createGetRequest(String url) {

    //log.debug "createGetRequest"
    //log.debug("/${url}")
    def result = new hubitat.device.HubAction(
        method: "GET",
        path: "/${url}",
        headers: [
            HOST: getHostAddress()
        ]
    )
    return result;
}

private postKey(String url, String key) {

    log.debug("/${url}")
    log.debug("KeyId:"+key);
    
    def result = new hubitat.device.HubAction(
            method: "POST",
            path: "/${url}",
            body: "KeyId="+key,
            headers: [
                HOST: getHostAddress(),
                "Content-Type": "application/x-www-form-urlencoded"
            ]
        )

        return result;
}

// gets the address of the device
private getHostAddress() {
    def ip = getDataValue("ip")
    def port = getDataValue("port")

    if (!ip || !port) {
        def parts = device.deviceNetworkId.split(":")
        if (parts.length == 2) {
            ip = parts[0]
            port = parts[1]
        } else {
            log.warn "Can't figure out ip and port for device: ${device.id}"
        }
    }

    log.debug "Using IP: ${convertHexToIP(ip)} and port: ${convertHexToInt(port)} for device: ${device.id}"
    return convertHexToIP(ip) + ":" + convertHexToInt(port)
}

private Integer convertHexToInt(hex) {
    return Integer.parseInt(hex,16)
}

private String convertHexToIP(hex) {
    return [convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}

//From this section on : leveraging JS code for AquaConnect HTTP Server, so all this code is copyrighted to the Hayward team
private String getFirstLedStatus(asciiByte) {
	return decodeRawLedData(extractNibbles(asciiByte).charAt(0))
}

private String getSecondLedStatus(asciiByte) {
	return decodeRawLedData(extractNibbles(asciiByte).charAt(1))
}

private String extractNibbles(asciiByte) 
{
	def TwoChars; 

    switch (asciiByte)
    {
    	case "3":
        	TwoChars = "33"; 
        	break;
      	case "4":
        	TwoChars = "34"; 
        	break;
      	case "5":
        	TwoChars = "35"; 
        	break;
      	case "6":
        	TwoChars = "36"; 
        	break;
    	case "C":
        	TwoChars = "43"; 
        	break;
      	case "D":
        	TwoChars = "44"; 
        	break;
      	case "E":
        	TwoChars = "45"; 
        	break;
      	case "F":
        	TwoChars = "46"; 
        	break;

    	case "S":
        	TwoChars = "53"; 
        	break;
      	case "T":
        	TwoChars = "54"; 
        	break;
      	case "U":
        	TwoChars = "55"; 
        	break;
      	case "V":
        	TwoChars = "56"; 
        	break;
    	case "c":
        	TwoChars = "63"; 
        	break;
      	case "d":
        	TwoChars = "64"; 
        	break;
      	case "e":
        	TwoChars = "65"; 
        	break;
      	case "f":
        	TwoChars = "66"; 
        	break;
      default:
        TwoChars = "00";
    }
	return TwoChars;	
} 

private decodeRawLedData(NibData)
{
    def StrClassData;

    switch ( NibData )
    {
    	case "3":
        	StrClassData = "NOKEY"; 
        	break;
      	case "4":
        	StrClassData = "off"; 
        	break;
      	case "5":
        	StrClassData = "on"; 
        	break;
      	case "6":
        	StrClassData = "BLINK" ; 
        	break;
      default:
        StrClassData = "WEBS_NOKEY"; 
    }
	
    return StrClassData; 

} 
