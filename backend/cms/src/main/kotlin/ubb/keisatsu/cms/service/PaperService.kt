package ubb.keisatsu.cms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.model.entities.Paper
import ubb.keisatsu.cms.model.entities.PaperDecision
import ubb.keisatsu.cms.repository.PaperRepository

@Service
class PaperService(private val paperRepository: PaperRepository) {
    fun addPaper(paper: Paper): Paper = paperRepository.save(paper)

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

    fun retrieveUploadedPapersWithoutCameraReadyCopy(): Collection<Paper> =
        retrieveAll().filter { paper -> paper.fullPaper != null && "" != paper.fullPaper &&
            (paper.cameraReadyCopy == null || "" == paper.cameraReadyCopy)}

    fun retrieveNotUploadedPapersHavingAuthor(author: Account): Collection<Paper> =
        retrievePapersHavingAuthor(author).filter { paper -> paper.fullPaper == null || "" == paper.fullPaper }

    fun retrievePapersHavingAuthorWithoutCameraReadyCopy(author: Account): Collection<Paper> =
        retrievePapersHavingAuthor(author).filter { paper ->
            (paper.cameraReadyCopy == null || "" == paper.cameraReadyCopy) && paper.decision == PaperDecision.ACCEPTED }
}
