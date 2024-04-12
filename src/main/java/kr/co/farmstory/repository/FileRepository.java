package kr.co.farmstory.repository;

import kr.co.farmstory.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
