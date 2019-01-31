/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mathe
 */
public class ConfigTest {

    public ConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Config.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Config result = Config.getInstance();
    }

    /**
     * Test of getLogsDir method, of class Config.
     */
    @Test
    public void testGetLogsDir() {
        System.out.println("getLogsDir");
        Config instance = Config.getInstance();
        String result = instance.getLogsDir();
    }

    /**
     * Test of getPicturesPath method, of class Config.
     */
    @Test
    public void testGetPicturesPath() {
        System.out.println("getPicturesPath");
        Config instance = Config.getInstance();
        String result = instance.getPicturesPath();
    }

    /**
     * Test of createDirectoryIfNotExists method, of class Config.
     */
    @Test
    public void testCreateDirectoryIfNotExists() {
        System.out.println("createDirectoryIfNotExists");
        String directoryPath = Config.getInstance().getPicturesPath();
        Config.createDirectoryIfNotExists(directoryPath);
    }

    /**
     * Test of getAppConfDir method, of class Config.
     */
    @Test
    public void testGetAppConfDir() {
        System.out.println("getAppConfDir");
        Config instance = Config.getInstance();
        String result = instance.getAppConfDir();
    }

    /**
     * Test of getProjectVersion method, of class Config.
     */
    @Test
    public void testGetProjectVersion() {
        System.out.println("getProjectVersion");
        Config instance = Config.getInstance();
        String result = instance.getProjectVersion();
    }

    /**
     * Test of getApiVersion method, of class Config.
     */
    @Test
    public void testGetApiVersion() {
        System.out.println("getApiVersion");
        Config instance = Config.getInstance();
        String result = instance.getApiVersion();
    }

}
