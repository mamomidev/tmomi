package org.hh99.tmomi.domain.stage.service

import jakarta.persistence.EntityExistsException
import org.hh99.tmomi.domain.stage.dto.stage.StageRequestDto
import org.hh99.tmomi.domain.stage.dto.stage.StageResponseDto
import org.hh99.tmomi.domain.stage.entity.Stage
import org.hh99.tmomi.domain.stage.repository.StageRepository
import org.hh99.tmomi.global.exception.GlobalException
import org.springframework.util.CollectionUtils
import spock.lang.Specification

class StageServiceTest extends Specification {
    StageService service
    StageRepository stageRepository = Mock(StageRepository)

    def setup() {
        service = new StageService(stageRepository)
    }

    def "getStageListByAddress"() {
        given:
        def stageRequestDto = Mock(StageRequestDto) {
            getAddress() >> "주소명"
        }
        stageRepository.findByAddressContaining(stageRequestDto.getAddress()) >> _stageList

        when:
        def address = service.getStageListByAddress(stageRequestDto)

        then:
        def empty = CollectionUtils.isEmpty(address)
        empty == false

        where:
        _stageList                 | _result
        Arrays.asList(Mock(Stage)) | true
    }

    def "getStageListByAddress - EntityExistsException"() {
        given:
        def stageRequestDto = Mock(StageRequestDto) {
            getAddress() >> "주소명"
        }
        1 * stageRepository.findByAddressContaining(stageRequestDto.getAddress()) >> _stageList

        when:
        service.getStageListByAddress(stageRequestDto)

        then:
        thrown(EntityExistsException)

        where:
        _stageList              | _result
        Collections.emptyList() | false
    }

    def "getStage"() {
        given:
        def stageId = 1L
        1 * stageRepository.findById(stageId) >> Optional.of(Mock(Stage))

        when:
        service.getStage(stageId)

        then:
        assert Optional.of(Mock(Stage)) != null
    }

    def "getStage - GlobalException"() {
        given:
        def stageId = 1L
        stageRepository.findById(stageId) >> _

        when:
        service.getStage(stageId)

        then:
        thrown(GlobalException)
    }

    def "createStage"() {
        given:
        def stageRequestDto = Mock(StageRequestDto)
        stageRepository.save(_ as Stage) >> { Stage savedStage -> savedStage }
        def stage = Mock(Stage) {
            id >> 1L
            address >> "주소"
        }

        when:
        def result = service.createStage(stageRequestDto)

        then:
        result instanceof StageResponseDto
    }

    def "updateStage"() {
        given:
        def stageId = 1L
        def stageRequestDto = Mock(StageRequestDto)
        stageRepository.findById(stageId) >> Optional.of(Mock(Stage))

        when:
        def result = service.updateStage(stageId, stageRequestDto)

        then:
        result instanceof StageResponseDto
    }

    def "deleteStage"() {
        given:
        def stageId = 1L

        stageRepository.findById(stageId) >> Optional.of(Mock(Stage))
        stageRepository.deleteById(stageId)

        when:
        def result = service.deleteStage(stageId)

        then:
        result instanceof StageResponseDto
    }
}