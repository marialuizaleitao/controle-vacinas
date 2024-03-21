package controller;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Aplicacao;
import service.AplicacaoService;

@Path("/aplicacao")
public class AplicacaoController {

	private AplicacaoService service = new AplicacaoService();

	@POST
	@Path("/salvar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Aplicacao salvar(Aplicacao novaAplicacao) {
		return service.salvar(novaAplicacao);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean atualizar(Aplicacao aplicacaoEditada) {
		return service.atualizar(aplicacaoEditada);
	}

	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) {
		return service.excluir(id);
	}

	@GET
	@Path("/todas")
	public List<Aplicacao> consultarTodas() {
		return service.consultarTodas();
	}
}