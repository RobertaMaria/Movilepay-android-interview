package com.roberta.movilepay_android_interview.repository.remote

import com.roberta.movilepay_android_interview.model.cardlist.Cards
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard
import com.roberta.movilepay_android_interview.repository.webClient.CardsService
import io.mockk.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardsRemoteDataSourceImpTest{
    private val cardService: CardsService = mockk()
    private val cardsRemoteDataSource: CardsRemoteDataSource = spyk(CardsRemoteDataSourceImp(cardService))

    @Test
    fun must_return_cards_when_api_return_success() {
        val success: (Cards) -> Unit = {}
        val successSlot = slot<(Cards) -> Unit>()
        val failure: (String) -> Unit = {}

        val callMock: Call<Cards> = mockk()
        val responseMock: Response<Cards> = mockk()

        every { cardService.getCards() } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<Cards>).apply {
                onResponse(mockk(), responseMock)
            }
        }

        responseMock.apply {
            every { isSuccessful } answers { true }
            every { body() } returns mockk()
        }

        run { cardsRemoteDataSource.getCards(success, failure) }

        verify { cardsRemoteDataSource.getCards(capture(successSlot), failure) }
    }

    @Test
    fun must_return_details_cards_when_api_return_success() {
        val success: (DetailsCard) -> Unit = {}
        val successSlot = slot<(DetailsCard) -> Unit>()
        val failure: (String) -> Unit = {}

        val callMock: Call<DetailsCard> = mockk()
        val responseMock: Response<DetailsCard> = mockk()

        every { cardService.getDetailsCards(123) } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<DetailsCard>).apply {
                onResponse(mockk(), responseMock)
            }
        }

        responseMock.apply {
            every { isSuccessful } answers { true }
            every { body() } returns mockk()
        }

        run { cardsRemoteDataSource.getDetailsCard(success, failure, 123) }

        verify { cardsRemoteDataSource.getDetailsCard(capture(successSlot), failure, 123) }
    }

    @Test
    fun must_return_details_account_when_api_return_success() {
        val success: (DetailsAccount) -> Unit = {}
        val successSlot = slot<(DetailsAccount) -> Unit>()
        val failure: (String) -> Unit = {}

        val callMock: Call<DetailsAccount> = mockk()
        val responseMock: Response<DetailsAccount> = mockk()

        every { cardService.getDetailsAccount(123) } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<DetailsAccount>).apply {
                onResponse(mockk(), responseMock)
            }
        }

        responseMock.apply {
            every { isSuccessful } answers { true }
            every { body() } returns mockk()
        }

        run { cardsRemoteDataSource.getDetailsAccount(success, failure, 123) }

        verify { cardsRemoteDataSource.getDetailsAccount(capture(successSlot), failure, 123) }
    }

    @Test
    fun must_return_card_failure_when_api_return_failure() {
        val success: (Cards) -> Unit = {}
        val failure: (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        val callMock: Call<Cards> = mockk()
        val responseMock: Response<Cards> = mockk()

        every { cardService.getCards() } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<Cards>).apply {
                onResponse(mockk(), responseMock)
            }
        }

        responseMock.apply {
            every { isSuccessful } answers { false }
            every { errorBody()?.string() } answers {"{}"}
        }

        run { cardsRemoteDataSource.getCards(success = success, failure = failure) }

        verify { cardsRemoteDataSource.getCards(success = any(), failure = capture(failureSlot)) }
    }

    @Test
    fun must_return_details_card_failure_when_api_return_failure() {
        val success: (DetailsCard) -> Unit = {}
        val failure: (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        val callMock: Call<DetailsCard> = mockk()
        val responseMock: Response<DetailsCard> = mockk()

        every { cardService.getDetailsCards(123) } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<DetailsCard>).apply {
                onResponse(mockk(), responseMock)
            }
        }

        responseMock.apply {
            every { isSuccessful } answers { false }
            every { errorBody()?.string() } answers {"{}"}
        }

        run { cardsRemoteDataSource.getDetailsCard(success = success, failure = failure, 123) }

        verify { cardsRemoteDataSource.getDetailsCard(success = any(), failure = capture(failureSlot), 123) }
    }

    @Test
    fun must_return_details_account_failure_when_api_return_failure() {
        val success: (DetailsAccount) -> Unit = {}
        val failure: (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        val callMock: Call<DetailsAccount> = mockk()
        val responseMock: Response<DetailsAccount> = mockk()

        every { cardService.getDetailsAccount(123) } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<DetailsAccount>).apply {
                onResponse(mockk(), responseMock)
            }
        }

        responseMock.apply {
            every { isSuccessful } answers { false }
            every { errorBody()?.string() } answers {"{}"}
        }

        run { cardsRemoteDataSource.getDetailsAccount(success = success, failure = failure, 123) }

        verify { cardsRemoteDataSource.getDetailsAccount(success = any(), failure = capture(failureSlot), 123) }
    }

    @Test
    fun must_devolve_failure_cards_when_the_connection_with_the_api_failure(){
        val success : (Cards) -> Unit = {}
        val failure : (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        val callMock: Call<Cards> = mockk()
        val throwableMock: Throwable = mockk()

        every { cardService.getCards() } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<Cards>).apply {
                onFailure(mockk(), throwableMock)
            }
        }

        throwableMock.apply {
            every { message } answers {""}
        }

        run { cardsRemoteDataSource.getCards(success, failure) }
        verify { cardsRemoteDataSource.getCards(any(), capture(failureSlot)) }

    }

    @Test
    fun must_devolve_failure_details_cards_when_the_connection_with_the_api_failure(){
        val success : (DetailsCard) -> Unit = {}
        val failure : (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        val callMock: Call<DetailsCard> = mockk()
        val throwableMock: Throwable = mockk()

        every { cardService.getDetailsCards(123) } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<DetailsCard>).apply {
                onFailure(mockk(), throwableMock)
            }
        }

        throwableMock.apply {
            every { message } answers {""}
        }

        run { cardsRemoteDataSource.getDetailsCard(success, failure, 123) }
        verify { cardsRemoteDataSource.getDetailsCard(any(), capture(failureSlot), 123) }

    }

    @Test
    fun must_devolve_failure_details_account_when_the_connection_with_the_api_failure(){
        val success : (DetailsAccount) -> Unit = {}
        val failure : (String) -> Unit = {}
        val failureSlot = slot<(String) -> Unit>()
        failureSlot.captured = failure

        val callMock: Call<DetailsAccount> = mockk()
        val throwableMock: Throwable = mockk()

        every { cardService.getDetailsAccount(123) } returns callMock
        every { callMock.enqueue(any()) } answers {
            (args[0] as Callback<DetailsAccount>).apply {
                onFailure(mockk(), throwableMock)
            }
        }

        throwableMock.apply {
            every { message } answers {""}
        }

        run { cardsRemoteDataSource.getDetailsAccount(success, failure, 123) }
        verify { cardsRemoteDataSource.getDetailsAccount(any(), capture(failureSlot), 123) }

    }
}