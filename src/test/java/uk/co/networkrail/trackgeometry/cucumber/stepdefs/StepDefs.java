package uk.co.networkrail.trackgeometry.cucumber.stepdefs;

import uk.co.networkrail.trackgeometry.CaptainsLogApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = CaptainsLogApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
