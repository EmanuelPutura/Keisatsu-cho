package ubb.keisatsu.cms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Paper
import ubb.keisatsu.cms.repository.PaperRepository

@Service
class PaperService(private val paperRepository: PaperRepository) {
    fun retrieveAll(): Iterable<Paper> = paperRepository.findAll()

    fun retrievePaper(paperID: Int): Paper? = paperRepository.findByIdOrNull(paperID)
}