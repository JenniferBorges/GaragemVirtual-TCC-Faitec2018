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
public class MailSenderTest {

    public MailSenderTest() {
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
     * Test of sendMail method, of class MailSender.
     */
    @Test
    public void testSendMail() throws Exception {
        System.out.println("sendMail");
        String to = "garagemvirtualfai@hotmail.com";
        String subject = "Teste";
        String message = "Teste";
        MailSender instance = new MailSender();
        instance.sendMail(to, subject, message);
    }

}
