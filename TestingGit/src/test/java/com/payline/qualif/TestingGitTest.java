package com.payline.qualif;

import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;




import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestingGitTest{
	private static List<WsdlTestCase> willBeExecuted;
	@BeforeClass
	public static void setUp(){
		WsdlProject project = null;
		try {
			ProxySelector proxy = ProxySelector.getDefault(); // On récupère le proxy courant.
			project = new WsdlProject("file:///"+System.getProperty("user.dir")+"/src/test/resources/project.xml");
			ProxySelector.setDefault(proxy); //On set le proxy précédemment enregistré car l'instanciation d'un WsdlProject efface le proxy, et cause des problèmes avec FirefoxWebdriver.
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<WsdlTestSuite> testsuites = new ArrayList<WsdlTestSuite>();
		willBeExecuted = new ArrayList<WsdlTestCase>();
		int countTS = project.getTestSuiteCount();
		for(int i=0; i < countTS; i++){
			testsuites.add(project.getTestSuiteAt(i));
		}

		for(WsdlTestSuite testsuite : testsuites){
			if(testsuite.isDisabled()){
				continue;
			}
			List<WsdlTestCase> testcases = new ArrayList<WsdlTestCase>();
			int countTC = testsuite.getTestCaseCount();
			for(int i=0; i < countTC; i++){
				testcases.add(testsuite.getTestCaseAt(i));
			}
			for(WsdlTestCase testcase : testcases){
				if(!testcase.isDisabled()){
					willBeExecuted.add(testcase);
				}
			}
		}
	}

	@Test
	public void method0(){
		WsdlTestCase tc = willBeExecuted.get(0);
		TestRunner runner = tc.run(new PropertiesMap(), false);
		Assert.assertEquals(Status.FINISHED, runner.getStatus());
	}


}
