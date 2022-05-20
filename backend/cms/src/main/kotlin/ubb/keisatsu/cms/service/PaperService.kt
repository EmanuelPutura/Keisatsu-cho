package ubb.keisatsu.cms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.Paper
import ubb.keisatsu.cms.repository.PaperRepository

@Service
class PaperService(private val paperRepository: PaperRepository) {
    fun retrieveAll(): Iterable<Paper> = paperRepository.findAll()

    fun retrievePaper(paperID: Int): Paper? = paperRepository.findByIdOrNull(paperID)

    fun retrievePapersHavingAuthor(author: Account): Collection<Paper> {
        val papers: MutableSet<Paper> = mutableSetOf()
        retrieveAll().forEach{ paper ->
            if (paper.paperAuthors.contains(author)) {
                papers.add(paper)
            }
        }

        return papers
    }

    fun retrieveNotUploadedPapersHavingAuthor(author: Account): Collection<Paper> {
        return retrievePapersHavingAuthor(author).filter { paper -> paper.file == null || "".equals(paper.file) }
    }
}