package dataconsumer.repository;

import dataconsumer.model.DataPoint;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface DataPointRepository extends CrudRepository<DataPoint, Long>, QueryByExampleExecutor<DataPoint> {

    <S extends DataPoint> Page<S> findAll(Example<S> example, Pageable pageable);
}