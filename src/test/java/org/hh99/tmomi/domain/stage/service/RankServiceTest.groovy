package org.hh99.tmomi.domain.stage.service

import org.hh99.tmomi.domain.stage.dto.rank.RankRequestDto
import org.hh99.tmomi.domain.stage.dto.rank.RankResponseDto
import org.hh99.tmomi.domain.stage.entity.Rank
import org.hh99.tmomi.domain.stage.entity.Seat
import org.hh99.tmomi.domain.stage.repository.RankRepository
import org.hh99.tmomi.domain.stage.repository.SeatRepository
import org.springframework.util.CollectionUtils
import spock.lang.Specification

class RankServiceTest extends Specification {
    RankService service
    RankRepository rankRepository = Mock(RankRepository)
    SeatRepository seatRepository = Mock(SeatRepository)

    def setup() {
        service = new RankService(rankRepository, seatRepository)
    }

    def "공연장 아이디로 등급 조회"() {
        given:
        def stageId = 1L
        rankRepository.findByStageId(stageId) >> Arrays.asList(Mock(Rank))

        when:
        service.getRankListByStageId(stageId)

        then:
        CollectionUtils.isEmpty(service.getRankListByStageId(stageId)) == false
    }

    def "등급 생성"() {
        given:
        def rankRequestDto = Mock(RankRequestDto)
        seatRepository.findById(rankRequestDto.getSeatId()) >> Optional.of(Mock(Seat))
        rankRepository.save(_ as Rank) >> Mock(Rank) {
            getId() >> 1L
            getSeat() >> Mock(Seat) {
                getId() >> 1L
            }
        }

        when:
        def result = service.createRank(rankRequestDto)

        then:
        result instanceof RankResponseDto
    }

    def "등급 수정"() {
        given:
        def rankId = 1L
        def rankRequestDto = Mock(RankRequestDto)

        seatRepository.findById(rankRequestDto.getSeatId()) >> Optional.of(Mock(Seat))
        rankRepository.findById(rankId) >> Optional.of(Mock(Rank) {
            getId() >> 1L
            getSeat() >> Mock(Seat) {
                getId() >> 1L
            }
        })
        Mock(Rank).updateSeatAndRankNameAndPrice(rankRequestDto, Mock(Seat))

        when:
        def result = service.updateRank(rankId, rankRequestDto)

        then:
        result instanceof RankResponseDto
    }

    def "등급 삭제"() {
        given:
        def rankId = 1L
        rankRepository.findById(rankId) >> Optional.of(Mock(Rank) {
            getSeat() >> Mock(Seat) {
                getId() >> 1L
            }
        })
        rankRepository.deleteById(rankId)

        when:
        def result = service.deleteRank(rankId)

        then:
        result instanceof RankResponseDto
    }
}