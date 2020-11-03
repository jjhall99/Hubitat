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
            //capability "Polling"
            capability "Refresh"
            capability "Temperature Measurement"
            capability "Configuration"
            //capability "Health Check"
            //capability "Indicator"
            
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
            attribute "switch", "String" 
            attribute "lights", "String"
            attribute "currentMode", "String"
            attribute "alertFlag", "String"
            
            command "componentOn";
            command "componentOff";
            //command "toggleLightMode(count)";
            //command "filter";
            //command "blower";
            //command "changeMode"
            //command "heater";
            command "toggleLights";
            //command "logsOff"
            command "on"
            command "off"
            
            command "createChildDevices";
            command "recreateChildDevices";
            command "deleteChildren";
            }
    preferences {
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
    	}
    //********************
    //SMARTTHINGS TILES
    //********************
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
        
        main "lights"
        details (["airTemp", "poolTemp", "spaTemp", "currentMode", "saltLevel", "chlorinatorStatus", "line1", "line2", "filter", "heater", "blower", "lights", "toggleLights", "dummyTile2"])
    */
    }   
    
def installed() {
    createChildDevices()
    initialize()
    refresh()
    log.warn "Parent Installed"
} 

def updated() {
    initialize()
    //unschedule()
    refresh()
    log.warn "Parent updated run"
    //refreshEvery5Seconds() //runs but does not refresh
    if (logEnable) runIn(1800,logsOff)
}

void refreshEvery5Seconds() {
   refresh()
   runIn(5, refreshEvery5Seconds)
}

def initialize() {
    //sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
    //sendEvent(name: "healthStatus", value: "online")
    //sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
    sendEvent(name: "poolTemp", value: "50", isStateChange: true)
    sendEvent(name: "spaTemp", value: "50", isStateChange: true)
    sendEvent(name: "airTemp", value: "50", isStateChange: true)
    sendEvent(name: "saltLevel", value: "1000", isStateChange: true)
    sendEvent(name: "chlorinatorStatus", value: "50", isStateChange: true)
    //schedule("0/5 * * * * ? *", refresh) //can't get this to work
    log.warn "initialize run"
} 

def createChildDevices() {
    log.warn "Parent createChildDevices"    
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.08", [name:"AquaConnect Child Device", label: "${device.name} Filter", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.09", [name:"AquaConnect Child Device", label: "${device.name} Lights", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.07", [name:"AquaConnect Child Device", label: "Spa", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.13", [name:"AquaConnect Child Device", label: "${device.name} Heater", isComponent:true])
        addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.0A", [name:"AquaConnect Child Device", label: "${device.name} Blower", isComponent:true])   
        //addChildDevice( "jjhall99", "AquaConnect Child", "${device.deviceNetworkId}.6", [name:"$device.displayName child.6", label: "superChlor", isComponent:true])    
    }
    
def recreateChildDevices() {
    log.warn "Parent recreateChildDevices"
    deleteChildren()
    createChildDevices()
}

def deleteChildren() {
	log.warn "Parent deleteChildren"
	def children = getChildDevices()
        children.each {child->
  		deleteChildDevice(child.deviceNetworkId)
    }
}  

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

private logDebug(msg) {
    if (settings?.logEnable || settings?.logEnable == null) {
        log.debug "$msg"
        }
}

def parse(String description) {
	//logDebug "Parsing '${description}'"
     
        def message = parseLanMessage(description)
        //logDebug(message)
        
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
        logDebug (line1)
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
        logDebug(line2) 
        sendEvent(name: "displayLine2", value: line2, isStateChange: true)

        def line3 = responseLines[2].substring(0, responseLines[2].length()-1)
        line3 = line3.substring(0, line3.length()-3)
        logDebug(line3)

    //Parse Pool Temp
        if(line1.contains("Pool Temp")) {
            line1 = line1.replaceAll("Pool Temp ", "")
            line1 = line1.replaceAll("°F", "")
            int con = line1 as Integer
            int poolTemp = device.currentValue("poolTemp")
            /*logDebug ("device.currentValue(pooltemp) is ${poolTemp}")
            if (poolTemp == con){
                //logDebug("Pool Temp unchanged")
                sendEvent(name: "poolTemp", value: con, isStateChange: false)
            }else {
                logDebug("Pool Temp changed from ${poolTemp} to ${con}")
                sendEvent(name: "poolTemp", value: con, isStateChange: true)
            }*/
            if (poolTemp == con){
                logDebug("Pool Temp unchanged")
                sendEvent(name: "poolTemp", value: con, isStateChange: false)
            }else {
                log.info("Pool Temp changed from ${poolTemp} to ${con}")
                sendEvent(name: "poolTemp", value: con, isStateChange: true)
            }
        
    //If Pool Mode, Sync Spa Temp
            if(getSecondLedStatus(line3.charAt(0)) == "on")/*Pool Mode*/{
                int spaTemp = device.currentValue("spaTemp") 
                //int spaTemp = 100
                if (spaTemp == con) {
                    logDebug("Spa Temp unchanged")
                    sendEvent(name: "spaTemp", value: con, isStateChange: false)
                }else {sendEvent(name: "spaTemp", value: con, isStateChange: true)
                log.info("Spa Temp changed from ${spaTemp} to ${con}")
                }
            }
        }

    //parse Spa Temp
        if(line1.contains("Spa Temp")) {
            line1 = line1.replaceAll("Spa Temp ", "")
            line1 = line1.replaceAll("°F", "")
            //logDebug("Spa temp updated")
            int con = line1 as Integer
            //sendEvent(name: "spaTemp", value: con, isStateChange: true)
            //logDebug ("con is ${con}")
            int spaTemp = device.currentValue("spaTemp")
            //logDebug ("device.currentValue(spatemp) is ${spaTemp}")
            if (spaTemp == con){
                logDebug("Spa Temp unchanged")
                sendEvent(name: "spaTemp", value: con, isStateChange: false)
            }else {
                log.info("Spa Temp changed from ${spaTemp} to ${con}")
                sendEvent(name: "spaTemp", value: con, isStateChange: true)
            }
        }
        
    //parse Air Temp    
        if(line1.contains("Air Temp")) {
            line1 = line1.replaceAll("Air Temp ", "")
            line1 = line1.replaceAll("°F", "")
            //logDebug("Air temp updated")
            int con = line1 as Integer
            //sendEvent(name: "airTemp", value: con, isStateChange: true)
            int airTemp = device.currentValue("airTemp")
            //logDebug ("device.currentValue(airtemp) is ${airTemp}")
            if (airTemp == con){
                logDebug("Air Temp unchanged")
                sendEvent(name: "airTemp", value: con, isStateChange: false)
            }else {
                log.info("Air Temp changed from ${airTemp} to ${con}")
                sendEvent(name: "airTemp", value: con, isStateChange: true)
            }
        }
        
    //parse Chlorinator Status    
        if(line1.contains("Chlorinator")) {
            line2 = line2.replaceAll("%", "")
            //logDebug("Pool Chlorinator status");
            int con = line2 as Integer
            //sendEvent(name: "chlorinatorStatus", value: con, isStateChange: true)
            int chlorinatorStatus = device.currentValue("chlorinatorStatus")
            //logDebug ("device.currentValue(chlorinatorStatus) is ${chlorinatorStatus}")
            if (chlorinatorStatus == con){
                logDebug("Chlorinator Status unchanged")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: false)
            }else {
                log.info("Chlorinator Status changed from ${chlorinatorStatus}% to ${con}%")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: true)
            }
        } 
        
    //update chlorinator status for super chlorinate    
        if(line1.contains("Super Chlorinate")) {
            //logDebug("Super Chlorinator On, updating pool chlorinator status");
            //sendEvent(name: "chlorinatorStatus", value: 100, isStateChange: true)
            int con = 100
            int chlorinatorStatus = device.currentValue("chlorinatorStatus")
            //logDebug ("device.currentValue(spatemp) is ${spaTemp}")
            if (chlorinatorStatus == con){
                logDebug("Chlorinator Status unchanged")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: false)
            }else {
                log.info("Chlorinator Status changed")
                sendEvent(name: "chlorinatorStatus", value: con, isStateChange: true)
            }
        }

        if(line1.contains("Salt Level")) {
            //line1 = line1.replaceAll("Salt Level ", "")
            line2 = line2.replaceAll(" PPM", "")
            //logDebug("Salt Level updated")
            int con = line2 as Integer
            //sendEvent(name: "saltLevel", value: con, isStateChange: true)
            //sendEvent(name: "saltLevel", value: line1, isStateChange: true)
            int saltLevel = device.currentValue("saltLevel")
            //logDebug ("device.currentValue(saltLevel) is ${saltLevel}")
            if (saltLevel == con){
                logDebug("Salt Level unchanged")
                sendEvent(name: "saltLevel", value: con, isStateChange: false)
            }else {
                log.info("Salt Level changed from ${saltLevel} to ${con}")
                sendEvent(name: "saltLevel", value: con, isStateChange: true)
            }
        }
        
        if(line1.contains("Check System")){
            sendEvent(name: "alertFlag", value: "${line2}", isStateChange: true)
            //def sayThis = '${line1}, ${line2}'
            //sendNotification("test message",[method:"push"])  //sendNotification("test notification - push", [method: "push"])
            //logDebug("${sayThis}")
        }
        
        def currentMode = device.currentValue("currentMode")
        //logDebug("Current Mode is ${currentMode}")
        //logDebug("getSecondLedStatus(line3.charAt(0)) == ${getSecondLedStatus(line3.charAt(0))}")
        if(getSecondLedStatus(line3.charAt(0)) == "on")/*Pool Mode*/{
            //logDebug("getFirstLedStatus(line3.charAt(0)) is off which should mean pool mode")
            if(currentMode=="Spa") {
                sendEvent(name: "currentMode", value: "Pool", isStateChange: true)
                logDebug("The Pool is now in Pool Mode")
                }else {sendEvent(name: "currentMode", value: "Pool", isStateChange: false)
                    //logDebug("Current Mode unchanged (Pool)")
                }
        } else { if(currentMode=="Spa") { 
            sendEvent(name: "currentMode", value: "Spa", isStateChange: false)
            logDebug("Current Mode unchanged (Spa)")
            } else {sendEvent(name: "currentMode", value: "Spa", isStateChange: true)
            log.info("The Pool is now in Spa Mode")}
        }
        
    //parse lights status                        
        /*def status = device.currentValue("switch")
        if(getSecondLedStatus(line3.charAt(2))=="on"){
            if(status=="off") {
                sendEvent(name: "switch", value: "on", isStateChange: true)
                logDebug("Pool Lights are now on")
                }else {sendEvent(name: "switch", value: "on", isStateChange: false)
                    //logDebug("Lights are on (unchanged)")
                }
        } else { if(status=="off"){
            sendEvent(name: "switch", value: "off", isStateChange: false)
            //logDebug("Lights are off (unchanged)")
            } else {sendEvent(name: "switch", value: "off", isStateChange: true)
            logDebug("Pool Lights are now off")}
        } */
        
        def lights = device.currentValue("lights")
        if(getSecondLedStatus(line3.charAt(2))=="on"){
            if(lights=="off") {
                sendEvent(name: "lights", value: "on", isStateChange: true)
//testing update child switch status
                def children = childDevices
                def childDevice = children.find{it.device.label.endsWith("Lights")} 
                    if(childDevice) {
                        log.info "Updating ${childDevice} switch to On"
		child.sendEvent(name: "switch", value: "on", isStateChange: true)
                }
                log.info("Pool Lights are now on")
                }else {sendEvent(name: "lights", value: "on", isStateChange: false)
                    logDebug("Lights are on (unchanged)")
                }
        } else { if(lights=="off"){
            sendEvent(name: "lights", value: "off", isStateChange: false)
            logDebug("Lights are off (unchanged)")
            } else {sendEvent(name: "lights", value: "off", isStateChange: true)
            log.info("Pool Lights are now off")}
        } 
    
    //parse filter status
        //def filter = device.currentValue("filter")
        filter = device.currentValue("filter")
        if(getFirstLedStatus(line3.charAt(2))=="on")/*filter on*/{
            if(filter=="off") {
                sendEvent(name: "filter", value: "on", isStateChange: true)
                logDebug("Pool Filter is now on")
                }else {sendEvent(name: "filter", value: "on", isStateChange: false)
                    //logDebug("Filter is on (unchanged)")
                }
        } else { if(filter=="off"){
            sendEvent(name: "filter", value: "off", isStateChange: false)
            //logDebug("Filter is off (unchanged)")
            } else {sendEvent(name: "filter", value: "off", isStateChange: true)
            logDebug("Pool Filter is now off")}
        }
    //parse heater status
        //if(getFirstLedStatus(line3.charAt(3))=="on")/*heater on*/{
        //    sendEvent(name: "heaterStatus", value: "on", isStateChange: true) 
        //}else sendEvent(name: "heaterStatus", value: "off", isStateChange: true)
    def heater = device.currentValue("heater")
        if(getFirstLedStatus(line3.charAt(3))=="on")/*heater on*/{
            if(heater=="off") {
                sendEvent(name: "heater", value: "on", isStateChange: true)
                log.info("Pool Heater is now on")
                }else {sendEvent(name: "heater", value: "on", isStateChange: false)
                    logDebug("Heater is on (unchanged)")
                }
        } else { if(heater=="off"){
            sendEvent(name: "heater", value: "off", isStateChange: false)
            logDebug("Heater is off (unchanged)")
            } else {sendEvent(name: "heater", value: "off", isStateChange: true)
            log.info("Pool Heater is now off")}
        }
    
    //parse blower status
        //if(getSecondLedStatus(line3.charAt(4))=="on"){
        //    sendEvent(name: "aux1Status", value: "on", isStateChange: true) 
        //}else sendEvent(name: "aux1Status", value: "off", isStateChange: true)
        def blower = device.currentValue("blower")
        if(getSecondLedStatus(line3.charAt(4))=="on")/*blower on*/{
            if(blower=="off") {
                sendEvent(name: "blower", value: "on", isStateChange: true)
                log.info("Blower is now on")
                }else {sendEvent(name: "blower", value: "on", isStateChange: false)
                    logDebug("Blower is on (unchanged)")
                }
        } else { if(blower=="off"){
            sendEvent(name: "blower", value: "off", isStateChange: false)
            logDebug("Blower is off (unchanged)")
            } else {sendEvent(name: "blower", value: "off", isStateChange: true)
            logDebug("Blower is now off")}
        }
}

def on() {
    log.info "Executing Master On for Pool"
    return postKey("WNewSt.htm", "00");
}

def off() {
    log.info "Executing Master Off for Pool"
    return postKey("WNewSt.htm", "00");   
}

def toggleLights(){
    int wait = 2*1 as Integer
    logDebug "Executing light toggle Off for ${wait} seconds"
    componentOff(lights)
    runIn (wait, componentOn(lights), [overwrite:true])
    //pause(wait)
    logDebug "Executing light toggle On"
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

def componentOn(device) {
    logDebug "Executing $device On"
    num = commandsMap."$device" ?: "default"
    logDebug "num: $num"
   return postKey("WNewSt.htm", "${num}");
   //return postKey("WNewSt.htm", num);
   //return postKey("WNewSt.htm", "0A");
}

def componentOff(device) {
    logDebug "Executing $device Off"
    num = commandsMap."$device" ?: "default"
    logDebug "num: $num"
   return postKey("WNewSt.htm", "${num}");
   //return postKey("WNewSt.htm", num);
   //return postKey("WNewSt.htm", "0A");
}
                    
def refresh() {
    return createGetRequest("WNewSt.htm");
    logDebug "parent refresh"
}

private getCallBackAddress() {
    return device.hub.getDataValue("localIP") + ":" + device.hub.getDataValue("localSrvPortTCP")
}

private createGetRequest(String url) {
    //logDebug "createGetRequest"
    //logDebug("/${url}")
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
    //logDebug("/${url}")
    //logDebug("KeyId:"+key);
    
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

    //logDebug "Using IP: ${convertHexToIP(ip)} and port: ${convertHexToInt(port)} for device: ${device.id}"
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
