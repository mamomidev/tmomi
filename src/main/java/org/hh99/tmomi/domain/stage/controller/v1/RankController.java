package org.hh99.tmomi.domain.stage.controller.v1;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RankController {

	@PostMapping("/v1/rank")
	public void createRank() {

	}

	@PutMapping("/v1/rank/{rankId}")
	public void updateRank(@PathVariable Long rankId) {

	}

	@DeleteMapping("/v1/rank/{rankId}")
	public void deleteRank(@PathVariable Long rankId) {

	}
}
