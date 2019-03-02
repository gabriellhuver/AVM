/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gab00
 */
public class ScriptMakerTest {

    public ScriptMakerTest() {
    }

    @Test
    public void testSomeMethod() {
        String scriptToEditPath = "C://temp//ScriptsCreated//scriptFinal.jsx";
        String saveFileFolder = "C://temp//ScriptsCreated//scriptFinal3.jsx";
        ScriptMaker.createScript(scriptToEditPath, 7, saveFileFolder);

    }

}
