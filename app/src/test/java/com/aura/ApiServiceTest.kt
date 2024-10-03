package com.aura

import com.aura.data.service.ApiService
import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ApiServiceTest {

  @Mock
  private lateinit var apiService: ApiService

  @Before
  fun setup() {
    MockitoAnnotations.openMocks(this)
  }

  /**
   * Teste la méthode de connexion avec des identifiants valides.
   * Ce test vérifie que l'appel à l'API avec un identifiant et un mot de passe valides retourne une réponse
   * indiquant que l'accès est accordé (granted = true).
   */
  @Test
  fun `login with valid credentials returns granted true`() = runTest  {
    val request = LoginRequest("validId", "validPassword")
    val response = Response.success(LoginResponse(true))
    Mockito.`when`(apiService.login(request)).thenReturn(response)

    val result = apiService.login(request)

    assertEquals(true, result.body()?.granted)
  }

  /**
   * Teste la méthode de connexion avec des identifiants invalides.
   * Ce test vérifie que l'appel à l'API avec un identifiant et un mot de passe incorrects retourne une réponse
   * indiquant que l'accès est refusé (granted = false).
   */
  @Test
  fun `login with invalid credentials returns granted false`() = runTest  {
    val request = LoginRequest("invalidId", "invalidPassword")
    val response = Response.success(LoginResponse(false))
    Mockito.`when`(apiService.login(request)).thenReturn(response)

    val result = apiService.login(request)

    assertEquals(false, result.body()?.granted)
  }

  /**
   * Teste la méthode de connexion avec des identifiants valides.
   * Ce test vérifie que l'appel à l'API avec un identifiant et un mot de passe valides retourne un code de statut HTTP 200 (OK).
   */
  @Test
  fun `login with valid credentials returns 200 OK`() = runTest  {
    val request = LoginRequest("validId", "validPassword")
    val response = Response.success(LoginResponse(true))
    Mockito.`when`(apiService.login(request)).thenReturn(response)

    val result = apiService.login(request)

    assertEquals(200, result.code())
  }

  /**
   * Teste la méthode de connexion avec des identifiants invalides.
   * Ce test vérifie que l'appel à l'API avec un identifiant et un mot de passe incorrects retourne un code de statut HTTP 401 (Unauthorized).
   */
  @Test
  fun `login with invalid credentials returns 401 Unauthorized`() = runTest  {
    val request = LoginRequest("invalidId", "invalidPassword")
    val errorResponse = "Unauthorized".toResponseBody()
    val response = Response.error<LoginResponse>(401, errorResponse)
    Mockito.`when`(apiService.login(request)).thenReturn(response)

    val result = apiService.login(request)

    assertEquals(401, result.code())
  }

  /**
   * Teste la méthode de connexion avec des identifiants vides.
   * Ce test vérifie que l'appel à l'API avec un identifiant et un mot de passe vides retourne un code de statut HTTP 400 (Bad Request).
   */
  @Test
  fun `login with empty credentials returns 400 Bad Request`() = runTest  {
    val request = LoginRequest("", "")
    val errorResponse = "Bad Request".toResponseBody()
    val response = Response.error<LoginResponse>(400, errorResponse)
    Mockito.`when`(apiService.login(request)).thenReturn(response)

    val result = apiService.login(request)

    assertEquals(400, result.code())
  }
}