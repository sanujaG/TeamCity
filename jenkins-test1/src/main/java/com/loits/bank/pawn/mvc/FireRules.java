package com.loits.bank.pawn.mvc;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class FireRules {

	//when inserting only one object
		public void runRules (Object object,String className,String packageName){
			try{
			String path=packageName+"."+className;
			
			KieServices ks=KieServices.Factory.get();
			KieContainer kc=ks.getKieClasspathContainer();
			KieSession kSession=kc.newKieSession("defaultKieSession");
			
			System.out.println("Ksession =========== " +kSession.getEntryPointId());
			kSession.addEventListener(new DebugAgendaEventListener());
			kSession.addEventListener(new DebugRuleRuntimeEventListener());
			Object dynamicO;
			try {
				dynamicO = Class.forName(path).newInstance();
			} catch (Exception e) {
				
				System.out.println(e);
			} 
			dynamicO = object;
			kSession.insert(dynamicO);
			kSession.fireAllRules();
			kSession.dispose();
			}catch (Exception e){
				System.out.println(e);
			}
		}	
		//when inserting 2 objects
		public void runRules (Object object1,String className1,Object object2,String className2,String packageName){
			
			String path;
			KieServices ks=KieServices.Factory.get();
			KieContainer kc=ks.getKieClasspathContainer();
			KieSession kSession=kc.newKieSession("defaultKieSession");
				
			kSession.addEventListener(new DebugAgendaEventListener());
			kSession.addEventListener(new DebugRuleRuntimeEventListener());
			
			try {
				Object dynamicO1,dynamicO2;
				
					path=packageName+"."+className1;
					dynamicO1= Class.forName(path).newInstance();
					dynamicO1= object1;
					kSession.insert(dynamicO1);
					path = packageName + "." + className2;
					dynamicO2 = Class.forName(path).newInstance();
					dynamicO2 = object2;
					kSession.insert(dynamicO2);
			} catch (Exception e) {
				System.out.println(e);
			} 
			kSession.fireAllRules();
			kSession.dispose();
		}

		//when inserting more than 2 objects
		public void runRules (Object object[],String packageName,String...className){
			
			String path;
			KieServices ks=KieServices.Factory.get();
			KieContainer kc=ks.getKieClasspathContainer();
			KieSession kSession=kc.newKieSession("defaultKieSession");
				
			kSession.addEventListener(new DebugAgendaEventListener());
			kSession.addEventListener(new DebugRuleRuntimeEventListener());
			
			try {
				Object dynamicO;
				int i=0;
				for(String name :className){
					path=packageName+"."+name;
					dynamicO= Class.forName(path).newInstance();
					dynamicO= object[i++];
					kSession.insert(dynamicO);
				}
			} catch (Exception e) {
				System.out.println(e);
			} 
			kSession.fireAllRules();
			kSession.dispose();
		}

}
