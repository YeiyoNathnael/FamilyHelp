package nathnael.yeiyo.adu.ac.ae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nathnael.yeiyo.adu.ac.ae.model.Interaction;
import nathnael.yeiyo.adu.ac.ae.service.InteractionService;

@RestController
@RequestMapping("/interactions")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Interaction createInteraction(@RequestBody Interaction interaction) {
        return interactionService.create(interaction);
    }
}
