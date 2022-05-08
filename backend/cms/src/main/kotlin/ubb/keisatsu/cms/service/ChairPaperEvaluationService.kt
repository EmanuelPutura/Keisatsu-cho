package ubb.keisatsu.cms.service

import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.ChairPaperEvaluation
import ubb.keisatsu.cms.repository.ChairPaperEvaluationRepository

@Service
class ChairPaperEvaluationService(private val chairPaperEvaluationRepository: ChairPaperEvaluationRepository) {
    fun getEvaluationByPaper(paperId: Int) = chairPaperEvaluationRepository.getByPaperId(paperId)

    fun addEvaluation(paperEvaluation: ChairPaperEvaluation) = chairPaperEvaluationRepository.save(paperEvaluation)
}