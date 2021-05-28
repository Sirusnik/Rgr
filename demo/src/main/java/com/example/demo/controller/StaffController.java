package com.example.demo.controller;

import com.example.demo.entity.Staff;
import com.example.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StaffController {

    @Qualifier("staffServiceImpl")
    @Autowired
    StaffService staffService;

    @RequestMapping(value = "/staff/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> getStaffById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Staff staff = (Staff) this.staffService.findById(id);

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(staff, HttpStatus.OK);
    }

    @RequestMapping(value = "/staff/findStaffByName",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> getStaffByName(@RequestParam(value = "name") String name) {
        if (name == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Staff staff = this.staffService.findByName(name);

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(staff, HttpStatus.OK);
    }

    @RequestMapping(value = "/staff", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> addStaff(@RequestBody Staff staff) {
        HttpHeaders headers = new HttpHeaders();
        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.staffService.save(staff);
        return new ResponseEntity<>(staff, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/staff/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> deleteStaff(@PathVariable("id") Long id) {
        Staff staff = (Staff) this.staffService.findById(id);

        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.staffService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/allStaff", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> allStaff = this.staffService.findAll();

        if (allStaff.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(allStaff, HttpStatus.OK);
    }
}
