package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequestMarker_Id(Long userId, Sort sort);

    List<Request> findAll(Sort sort);

    List<Request> findAllByRequestMarker_IdNot(Long userId, Pageable pageable);

}
