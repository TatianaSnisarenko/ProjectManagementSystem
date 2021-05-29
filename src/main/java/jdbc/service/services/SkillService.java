package jdbc.service.services;

import jdbc.dao.entity.SkillDao;
import jdbc.dao.repositories.one_entity_repositories.Repository;
import jdbc.dto.SkillTo;
import jdbc.service.converters.SkillConverter;

import java.util.List;

public class SkillService {
    private Repository<SkillDao> repository;

    public SkillService(Repository<SkillDao> repository) {
        this.repository = repository;
    }

    public SkillTo create(SkillTo skillTo) {
        SkillDao skillDao = SkillConverter.toSkillDao(skillTo);
        SkillDao createdSkillDao = repository.create(skillDao);
        return SkillConverter.fromSkillDao(repository.findById(createdSkillDao.getIdSkill()));
    }

    public SkillTo findById(int companyId) {
        return SkillConverter.fromSkillDao(repository.findById(companyId));
    }

    public SkillTo update(SkillTo SkillTo) {
        SkillDao updatedSkillDao = repository.update(SkillConverter.toSkillDao(SkillTo));
        return SkillConverter.fromSkillDao(updatedSkillDao);
    }

    public SkillTo deleteById(int companyId) {
        return SkillConverter.fromSkillDao(repository.deleteById(companyId));
    }

    public SkillTo deleteByObject(SkillTo SkillTo) {
        SkillDao deletedSkillDao = repository.deleteByObject(SkillConverter.toSkillDao(SkillTo));
        return SkillConverter.fromSkillDao(deletedSkillDao);
    }

    public List<SkillTo> findAll() {
        return SkillConverter.allFromSkillDao(repository.findAll());
    }
}
