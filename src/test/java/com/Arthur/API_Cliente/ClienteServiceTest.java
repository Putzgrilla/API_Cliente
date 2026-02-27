package com.Arthur.API_Cliente;

import com.Arthur.API_Cliente.DTO.ClienteAtualizarDTO;
import com.Arthur.API_Cliente.DTO.ClienteDto;
import com.Arthur.API_Cliente.DTO.ClienteSalvarDTO;
import com.Arthur.API_Cliente.entity.Cliente;
import com.Arthur.API_Cliente.entity.Endereco;
import com.Arthur.API_Cliente.exception.NotFoundException;
import com.Arthur.API_Cliente.mapper.ClienteMapper;
import com.Arthur.API_Cliente.mapper.ClienteMapperImpl;
import com.Arthur.API_Cliente.mapper.EnderecoMapper;
import com.Arthur.API_Cliente.mapper.EnderecoMapperImpl;
import com.Arthur.API_Cliente.repository.ClienteRepository;
import com.Arthur.API_Cliente.service.ClienteService;
import com.Arthur.API_Cliente.service.ViaCepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Spy
    private EnderecoMapper enderecoMapper = new EnderecoMapperImpl();
    @Spy
    private ClienteMapper clienteMapper = new ClienteMapperImpl(enderecoMapper);

    @Mock
    private ClienteRepository repository;
    @Mock
    private ViaCepService viaCepService;

    private Cliente cliente;
    private Endereco endereco;

    @BeforeEach
    public void setup() {
        endereco = new Endereco();
        endereco.setNumero("120");
        endereco.setBairro("jardim");
        endereco.setLogradouro("rua ali");
        endereco.setUf("sp");
        endereco.setLocalidade("Moji");
        endereco.setCep("65605635");

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Jorge");
        cliente.setNascimento(LocalDate.of(2003, 10, 10));
        cliente.setEndereco(endereco);
    }

    // ========================
    //  salvar()
    // ========================

    @Test
    void salvar_deveRetornarClienteDto_quandoDadosValidos() {
        ClienteSalvarDTO dto = new ClienteSalvarDTO("Jorge", LocalDate.of(2003, 10, 10), "65605-635", "120");

        when(viaCepService.buscarEnderecoPorCep(anyString(), any())).thenReturn(endereco);
        when(repository.save(any())).thenReturn(cliente);

        ClienteDto resultado = clienteService.salvar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Jorge", resultado.nome());
    }

    @Test
    void salvar_deveChamarViaCepService_comCepENumeroCorretos() {
        ClienteSalvarDTO dto = new ClienteSalvarDTO("Jorge", LocalDate.of(2003, 10, 10), "65605-635", "120");

        when(viaCepService.buscarEnderecoPorCep(anyString(), any())).thenReturn(endereco);
        when(repository.save(any())).thenReturn(cliente);

        clienteService.salvar(dto);

        verify(viaCepService, times(1)).buscarEnderecoPorCep("65605-635", "120");
    }

    @Test
    void salvar_devePersistirCliente_umaVez() {
        ClienteSalvarDTO dto = new ClienteSalvarDTO("Jorge", LocalDate.of(2003, 10, 10), "65605-635", "120");

        when(viaCepService.buscarEnderecoPorCep(anyString(), any())).thenReturn(endereco);
        when(repository.save(any())).thenReturn(cliente);

        clienteService.salvar(dto);

        verify(repository, times(1)).save(any(Cliente.class));
    }

    // ========================
    //  buscarPorId()
    // ========================

    @Test
    void buscarPorId_deveRetornarClienteDto_quandoIdExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<ClienteDto> resultado = clienteService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().id());
        assertEquals("Jorge", resultado.get().nome());
    }

    @Test
    void buscarPorId_deveRetornarOptionalVazio_quandoIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<ClienteDto> resultado = clienteService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void buscarPorId_deveChamarRepository_comIdCorreto() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.buscarPorId(1L);

        verify(repository, times(1)).findById(1L);
    }

    // ========================
    //  buscarTodos()
    // ========================

    @Test
    void buscarTodos_deveRetornarListaDeClientes_quandoExistiremRegistros() {
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Maria");
        cliente2.setNascimento(LocalDate.of(1995, 5, 20));
        cliente2.setEndereco(endereco);

        when(repository.findAll()).thenReturn(List.of(cliente, cliente2));

        List<ClienteDto> resultado = clienteService.buscarTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(ClienteDto::nome).containsExactly("Jorge", "Maria");
    }

    @Test
    void buscarTodos_deveRetornarListaVazia_quandoNaoHouverRegistros() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<ClienteDto> resultado = clienteService.buscarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ========================
    //  deletar()
    // ========================

    @Test
    void deletar_deveDeletarComSucesso_quandoIdExistente() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> clienteService.deletar(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_deveLancarNotFoundException_quandoIdInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> clienteService.deletar(99L));

        assertThat(ex.getMessage()).contains("99");
        verify(repository, never()).deleteById(anyLong());
    }

    // ========================
    //  atualizar()
    // ========================

    @Test
    void atualizar_deveAtualizarNome_quandoNomeInformado() {
        ClienteAtualizarDTO dto = new ClienteAtualizarDTO("NovoNome", null, null, null);

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any())).thenReturn(cliente);

        ClienteDto resultado = clienteService.atualizar(dto, 1L);

        assertEquals("NovoNome", resultado.nome());
        verify(viaCepService, never()).buscarEnderecoPorCep(anyString(), any());
    }

    @Test
    void atualizar_deveAtualizarEndereco_quandoCepInformado() {
        Endereco novoEndereco = new Endereco();
        novoEndereco.setCep("01001-000");
        novoEndereco.setLogradouro("Praça da Sé");
        novoEndereco.setBairro("Sé");
        novoEndereco.setLocalidade("São Paulo");
        novoEndereco.setUf("SP");
        novoEndereco.setNumero("1");

        ClienteAtualizarDTO dto = new ClienteAtualizarDTO(null, null, "01001-000", "1");

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(viaCepService.buscarEnderecoPorCep("01001-000", "1")).thenReturn(novoEndereco);
        when(repository.save(any())).thenReturn(cliente);

        clienteService.atualizar(dto, 1L);

        verify(viaCepService, times(1)).buscarEnderecoPorCep("01001-000", "1");
    }

    @Test
    void atualizar_deveAtualizarNascimento_quandoNascimentoInformado() {
        LocalDate novaData = LocalDate.of(2000, 1, 1);
        ClienteAtualizarDTO dto = new ClienteAtualizarDTO(null, novaData, null, null);

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any())).thenReturn(cliente);

        ClienteDto resultado = clienteService.atualizar(dto, 1L);

        assertNotNull(resultado);
        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    void atualizar_deveLancarNotFoundException_quandoClienteNaoEncontrado() {
        ClienteAtualizarDTO dto = new ClienteAtualizarDTO("Teste", null, null, null);

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clienteService.atualizar(dto, 99L));
        verify(repository, never()).save(any());
    }

    @Test
    void atualizar_naoDeveAlterarCampos_quandoTodosCamposNulos() {
        ClienteAtualizarDTO dto = new ClienteAtualizarDTO(null, null, null, null);

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any())).thenReturn(cliente);

        ClienteDto resultado = clienteService.atualizar(dto, 1L);

        assertEquals("Jorge", resultado.nome());
        verify(viaCepService, never()).buscarEnderecoPorCep(anyString(), any());
    }
}