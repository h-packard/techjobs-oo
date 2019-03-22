package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);

        model.addAttribute("title", "Job Detail");
        model.addAttribute("job", job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "Add Job");
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");

            return "new-job";
        }
        //all int types to get values from ids
        int empId = jobForm.getEmployerId();
        int locId = jobForm.getLocationId();
        int posTypeId = jobForm.getPositionTypeId();
        int coreCompId = jobForm.getCoreCompetencyId();

        String aName = jobForm.getName();
        Employer anEmployer = jobData.getEmployers().findById(empId);
        Location aLocation = jobData.getLocations().findById(locId);
        PositionType aPostitionType = jobData.getPositionTypes().findById(posTypeId);
        CoreCompetency aSkill = jobData.getCoreCompetencies().findById(coreCompId);

        //new job object - populate with newly gen obj
        Job job = new Job(aName,anEmployer,aLocation,aPostitionType,aSkill);

        jobData.add(job);

        int id = job.getId();

        return "redirect:?id="+id;

    }
}
