package test.com.github.kurtloong.bifrost.heimdall.core.mappings; 

import com.alibaba.testable.core.tool.OmniConstructor;
import com.github.kurtloong.bifrost.heimdall.core.mappings.RouteMapping;
import com.github.kurtloong.bifrost.heimdall.domain.entity.ServerConfigEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/** 
* RouteMapping Tester. 
* 
* @author <Authors name> 
* @since <pre>4ÔÂ 28, 2021</pre> 
* @version 1.0 
*/ 
public class RouteMappingTest { 
    RouteMapping routeMapping = new RouteMapping();
    ServerConfigEntity serverConfigEntity;
    ServerConfigEntity serverConfigEntity2;
    ServerConfigEntity serverConfigEntity3;
    ServerConfigEntity serverConfigEntity4;
    ServerConfigEntity serverConfigEntity5;
    List<ServerConfigEntity> list = new ArrayList<>();

@Before
public void before() throws Exception {
    serverConfigEntity = OmniConstructor.newInstance(ServerConfigEntity.class);
    serverConfigEntity.setHost("127.0.0.1:8080");
    serverConfigEntity.setName("testServer");
    serverConfigEntity2 = OmniConstructor.newInstance(ServerConfigEntity.class);
    serverConfigEntity3 = OmniConstructor.newInstance(ServerConfigEntity.class);
    serverConfigEntity2.setName("testServer");
    serverConfigEntity2.setHost("183.214.21.21:8080");
    serverConfigEntity3.setName("testServer2");
    serverConfigEntity3.setHost("127.0.0.1:8080");
    serverConfigEntity4 = OmniConstructor.newInstance(ServerConfigEntity.class);
    serverConfigEntity4.setHost("123.123.123.2:8080");
    serverConfigEntity4.setName("testServer");
    serverConfigEntity5 = OmniConstructor.newInstance(ServerConfigEntity.class);
    serverConfigEntity5.setName("testServer3");
    serverConfigEntity5.setHost("127.0.0.1:8080");
    list.add(serverConfigEntity4);
    list.add(serverConfigEntity5);
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: put(ServerConfigEntity serverConfigEntity) 
* 
*/ 
@Test
public void testPut() throws Exception {
    routeMapping.put(serverConfigEntity);
    Assert.assertTrue(routeMapping.getRouteMap().size()>0);
    Assert.assertTrue(routeMapping.getRouteMap().get("testServer").size()>0);
    routeMapping.put(serverConfigEntity2);
    Assert.assertTrue(routeMapping.getRouteMap().size()<2);
    Assert.assertTrue(routeMapping.getRouteMap().get("testServer").size()>1);
    routeMapping.put(serverConfigEntity3);
    Assert.assertTrue(routeMapping.getRouteMap().size()>1);
} 

/** 
* 
* Method: remove(ServerConfigEntity serverConfigEntity) 
* 
*/ 
@Test
public void testRemove() throws Exception {
    testPut();
    routeMapping.remove(serverConfigEntity3);
    Assert.assertFalse(routeMapping.getRouteMap().containsKey("testServer2"));

} 

/** 
* 
* Method: update(List<ServerConfigEntity> servers, Integer version) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    testPut();
    Integer integer = routeMapping.getVersion().get();
    routeMapping.update(list,integer);
    Assert.assertTrue(routeMapping.getRouteMap().size()>2);
    Assert.assertTrue(routeMapping.getRouteMap().get("testServer").size()>2);
    routeMapping.update(list,999);
    Assert.assertEquals(2, routeMapping.getRouteMap().size());
    Assert.assertEquals(1, routeMapping.getRouteMap().get("testServer").size());

} 



/** 
* 
* Method: getRouteMap() 
* 
*/ 
@Test
public void testGetRouteMap() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getVersion() 
* 
*/ 
@Test
public void testGetVersion() throws Exception { 
//TODO: Test goes here... 
} 


} 
