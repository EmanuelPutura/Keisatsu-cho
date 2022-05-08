package ubb.keisatsu.cms.repository;

import org.springframework.data.repository.CrudRepository
import ubb.keisatsu.cms.model.entities.ChairPaperEvaluation

interface ChairPaperEvaluationRepository : CrudRepository<ChairPaperEvaluation, Int> {
    fun getByPaperId(paperId: Int): Iterable<ChairPaperEvaluation>


}