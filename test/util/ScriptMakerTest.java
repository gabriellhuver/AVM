/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import core.AVMWorkflow;
import org.junit.Test;

/**
 *
 * @author gab00
 */
public class ScriptMakerTest {

    public ScriptMakerTest() {
    }

    @Test
    public void testSomeMethod() {
        String scriptToEditPath = "C://temp//AVM//ScriptsCreated//ScriptTemplate.jsx";
        String saveFileFolder = "C://temp//AVM//ScriptsCreated//scriptFinalV2.jsx";
        //ScriptMaker.createScript(scriptToEditPath, 7, saveFileFolder);
        //log("Saving links on database!");
        //LinkDatabase linkDatase = JSONUtil.getlinkDatase("C:\\temp\\AVM\\clipsDatabase.json");
        AVMWorkflow.createTempAfterEffectsScript();
        
        //linkDatase.getLinks().add("http://test.tv.com/clip1/byme");
        
        //JSONUtil.saveLinkDatabase("C:\\temp\\AVM\\clipsDatabase.json", linkDatase);
    }

}
