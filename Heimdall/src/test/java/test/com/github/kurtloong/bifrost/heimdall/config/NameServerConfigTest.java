package test.com.github.kurtloong.bifrost.heimdall.config; 

import com.github.kurtloong.bifrost.heimdall.config.NameServerConfig;
import com.github.kurtloong.bifrost.heimdall.config.properties.NameServerConfigProperties;
import com.github.kurtloong.bifrost.heimdall.domain.entity.SocketEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 
* NameServerConfig Tester. 
* 
* @author <Authors name> 
* @since <pre>4ÔÂ 26, 2021</pre> 
* @version 1.0 
*/ 
public class NameServerConfigTest {
    private List<String> testList = new ArrayList<>();

@Before
public void before() throws Exception {
    testList.add("http://127.0.0.1:8080");
    testList.add("http://127.0.0.2:8080");
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: nameServerSockets(NameServerConfigProperties configProperties) 
* 
*/ 
@Test
public void testNameServerSockets() throws Exception {
    NameServerConfig nameServerConfig = new NameServerConfig();
    NameServerConfigProperties nameServerConfigProperties = new NameServerConfigProperties();
    nameServerConfigProperties.setNameSeverAddress(testList);
    nameServerConfigProperties.setCluster(false);
    HashMap<String, SocketEntity> socketEntityHashMap =  nameServerConfig.nameServerSockets(nameServerConfigProperties);
    Assert.assertTrue(socketEntityHashMap.size()<1);
} 


} 
