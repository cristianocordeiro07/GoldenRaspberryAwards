package com.github.cristianocordeiro07.gra.repository;

import com.github.cristianocordeiro07.gra.model.WorstMovie;
import com.github.cristianocordeiro07.gra.model.dto.ProducerAwardsIntervalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorstMovieRepository extends JpaRepository<WorstMovie, Long> {

    @SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
    @Query(value = "select top 1 WITH TIES * from " +
            "( " +
            "   select producer_id as producer, year as previousWin, " +
            "   lead(year) over (partition by producer_id order by producer_id) as followingWin, " +
            "   lead(year) over (partition by producer_id order by producer_id)  - year as interval_ " +
            "   from worst_movie wm " +
            "   inner join worst_movie_producer wmp on wm.id = wmp.worst_movie_id where winner = 1 " +
            ") " +
            "where followingWin is not null " +
            "order by interval_ asc", nativeQuery = true)
    List<ProducerAwardsIntervalDTO.ProducerAwardsIntervalObjectDTO> getProducerAwardsMinInterval();

    @SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
    @Query(value = "select top 1 WITH TIES * from " +
            "( " +
            "   select producer_id as producer, year as previousWin, " +
            "   lead(year) over (partition by producer_id order by producer_id) as followingWin, " +
            "   lead(year) over (partition by producer_id order by producer_id)  - year as interval_ " +
            "   from worst_movie wm " +
            "   inner join worst_movie_producer wmp on wm.id = wmp.worst_movie_id where winner = 1 " +
            ") " +
            "where followingWin is not null " +
            "order by interval_ desc", nativeQuery = true)
    List<ProducerAwardsIntervalDTO.ProducerAwardsIntervalObjectDTO> getProducerAwardsMaxInterval();
}
