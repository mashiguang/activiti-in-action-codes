package cn.niceabc.activiti.no_spring;

import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.*;

public class ActivitiRuleTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    private ProcessEngine processEngine;
    private IdentityService identityService;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;

    @Test
    @Deployment(resources = "processes/leave.bpmn20.xml")
    public void test() {

        //create process definition
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .singleResult();
        Assert.assertNotNull(processDefinition);
        Assert.assertEquals("leave", processDefinition.getKey());



        //start a process instance
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("leave");
        Assert.assertNotNull(processInstance);


        //get a task, complete it.
        //Task task_manager = taskService.createTaskQuery().taskCandidateGroup("manager").singleResult();
        Task task_manager = taskService.createTaskQuery().taskCandidateUser("tom").singleResult();
        Assert.assertNotNull(task_manager);
        Assert.assertEquals("verify-by-manager", task_manager.getName());
        taskService.complete(task_manager.getId());


        //check that the task has been completed.
        ProcessInstance processInstance1_uncompleted = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        // if the processInstance is null then the instance was completed.
        Assert.assertNotNull(processInstance1_uncompleted);


        //get a task, complete it.
        //Task task_hr = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
        Task task_hr = taskService.createTaskQuery().taskCandidateUser("john").singleResult();
        Assert.assertNotNull(task_hr);
        Assert.assertEquals("verify-by-hr", task_hr.getName());
        taskService.complete(task_hr.getId());


        //check that the task has been completed.
        ProcessInstance processInstance1_completed = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        // if the processInstance is null then the instance was completed.
        Assert.assertNull(processInstance1_completed);

    }

    @Before
    public void before() {
        /*processEngine = ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration()
                .buildProcessEngine();*/
        identityService = activitiRule.getIdentityService();
        repositoryService = activitiRule.getRepositoryService();
        runtimeService = activitiRule.getRuntimeService();
        taskService = activitiRule.getTaskService();

        //group

        Group group_manager =identityService.newGroup("manager");
        group_manager.setName("manager");
        identityService.saveGroup(group_manager);

        Group group_hr =identityService.newGroup("hr");
        group_hr.setName("hr");
        identityService.saveGroup(group_hr);


        //user

        User user = identityService.newUser("tom");
        user.setFirstName("tom");
        user.setLastName("cruise");
        user.setEmail("tom.cruise@email.com");
        identityService.saveUser(user);

        User user_john = identityService.newUser("john");
        user_john.setFirstName("john");
        user_john.setLastName("snow");
        user_john.setEmail("john.snow@email.com");
        identityService.saveUser(user_john);

        User user_max = identityService.newUser("max");
        user_max.setFirstName("max");
        user_max.setLastName("ma");
        user_max.setEmail("max.ma@email.com");
        identityService.saveUser(user_max);




        // membership
        identityService.createMembership("tom", "manager");
        identityService.createMembership("john", "hr");

    }

    @After
    public void after() {
        /*identityService.deleteMembership("tom", "manager");
        identityService.deleteGroup("manager");
        identityService.deleteUser("tom");*/

    }
}
