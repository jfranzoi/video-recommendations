package my.projects.videorecommendations.dummies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EmptySpecificationRepository<T> implements JpaSpecificationExecutor<T> {
    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return Optional.empty();
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return findAll();
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return new PageImpl<>(findAll(spec));
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return findAll(spec);
    }

    @Override
    public long count(Specification<T> spec) {
        return 0;
    }

    @Override
    public boolean exists(Specification<T> spec) {
        return false;
    }

    @Override
    public long delete(Specification<T> spec) {
        return 0;
    }

    @Override
    public <S extends T, R> R findBy(Specification<T> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    protected <T> List<T> findAll() {
        return List.of();
    }
}
