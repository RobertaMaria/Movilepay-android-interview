package com.roberta.movilepay_android_interview.repository

import com.roberta.movilepay_android_interview.model.cardlist.Cards
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard
import com.roberta.movilepay_android_interview.repository.remote.CardsRemoteDataSource
import com.roberta.movilepay_android_interview.repository.remote.CardsRemoteDataSourceImp
import com.roberta.movilepay_android_interview.repository.webClient.CardsService
import io.mockk.*
import org.junit.Test

class CardsRepositoryImpTest {
    private val cardService: CardsService = mockk()
    private val cardsRemoteDataSource: CardsRemoteDataSource = spyk(CardsRemoteDataSourceImp(cardService))
    private val repository: CardsRepository = spyk(CardsRepositoryImp(cardsRemoteDataSource))

    @Test
    fun must_receive_list_cards_when_api_return_success(){
        val sucess: (Cards) -> Unit = {}
        val sucessSlot = slot<(Cards) -> Unit>()
        val failure: (String) -> Unit = {}

        every { cardsRemoteDataSource.getCards(success = any(), failure = any()) } answers {
            firstArg<(Cards) -> Unit>().invoke(mockk())
        }

        run { repository.getCards(success = sucess, failure = failure) }

        verify { repository.getCards(success = capture(sucessSlot), failure = any()) }
    }

    @Test
    fun must_receive_details_cards_when_api_return_success(){
        val sucess: (DetailsCard) -> Unit = {}
        val sucessSlot = slot<(DetailsCard) -> Unit>()
        val failure: (String) -> Unit = {}

        every { cardsRemoteDataSource.getDetailsCard(success = any(), failure = any(), id = 123) } answers {
            firstArg<(DetailsCard) -> Unit>().invoke(mockk())
        }

        run { repository.getDetailsCard(success = sucess, failure = failure, id = 123) }

        verify { repository.getDetailsCard(success = capture(sucessSlot), failure = any(), id = 123) }
    }

    @Test
    fun must_receive_details_account_when_api_return_success(){
        val sucess: (DetailsAccount) -> Unit = {}
        val sucessSlot = slot<(DetailsAccount) -> Unit>()
        val failure: (String) -> Unit = {}

        every { cardsRemoteDataSource.getDetailsAccount(success = any(), failure = any(), id = 123) } answers {
            firstArg<(DetailsAccount) -> Unit>().invoke(mockk())
        }

        run { repository.getDetailsAccount(success = sucess, failure = failure, id = 123) }

        verify { repository.getDetailsAccount(success = capture(sucessSlot), failure = any(), id = 123) }
    }

    @Test
    fun must_notify_cards_failure_when_api_return_failure() {
        val sucess: (Cards) -> Unit = {}
        val failure: (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        every { cardsRemoteDataSource.getCards(any(), any()) } answers {
            secondArg<(String) -> Unit>().invoke("teste")
        }

        run { repository.getCards(success = sucess, failure = failure) }

        verify { repository.getCards(success = any(), failure = capture(failureSlot)) }

    }

    @Test
    fun must_notify_details_cards_failure_when_api_return_failure() {
        val sucess: (DetailsCard) -> Unit = {}
        val failure: (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        every { cardsRemoteDataSource.getDetailsCard(any(), any(), 123) } answers {
            secondArg<(String) -> Unit>().invoke("teste")
        }

        run { repository.getDetailsCard(success = sucess, failure = failure, id = 123) }

        verify { repository.getDetailsCard(success = any(), failure = capture(failureSlot), id = 123) }

    }

    @Test
    fun must_notify_details_account_failure_when_api_return_failure() {
        val sucess: (DetailsAccount) -> Unit = {}
        val failure: (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        every { cardsRemoteDataSource.getDetailsAccount(any(), any(), 123) } answers {
            secondArg<(String) -> Unit>().invoke("teste")
        }

        run { repository.getDetailsAccount(success = sucess, failure = failure, id = 123) }

        verify { repository.getDetailsAccount(success = any(), failure = capture(failureSlot), id = 123) }

    }

}