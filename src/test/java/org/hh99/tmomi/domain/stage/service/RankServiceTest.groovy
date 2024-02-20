package org.hh99.tmomi.domain.stage.service

import org.hh99.tmomi.domain.stage.repository.RankRepository
import spock.lang.Specification

class RankServiceTest extends Specification {
    RankService service
    RankRepository rankRepository = Mock(RankRepository)

    def setup() {
        service = new RankService(rankRepository)
    }
}