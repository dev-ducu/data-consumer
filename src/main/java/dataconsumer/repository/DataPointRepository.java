package dataconsumer.repository;

import dataconsumer.model.DataPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DataPointRepository extends MongoRepository<DataPoint, Long> {

    @Query("{ $query: {publisher: $0}, $orderby: {$natural : -1} }")
    List<DataPoint> findLastNEntriesOfPublisher(String publisher, int n);

}