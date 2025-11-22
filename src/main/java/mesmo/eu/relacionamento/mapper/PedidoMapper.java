package mesmo.eu.relacionamento.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import jakarta.persistence.EntityNotFoundException;
import mesmo.eu.relacionamento.dto.ItemDto;
import mesmo.eu.relacionamento.dto.pedido.PedidoDto;
import mesmo.eu.relacionamento.entity.Item;
import mesmo.eu.relacionamento.entity.ItemId;
import mesmo.eu.relacionamento.entity.Pedido;
import mesmo.eu.relacionamento.entity.Produto;
import mesmo.eu.relacionamento.repository.ProdutoRepository;

@Mapper(componentModel="spring")
public interface PedidoMapper
{
    @Mapping(target="itens", source="itens")
    PedidoDto toDto(Pedido pedido);

    @InheritInverseConfiguration
    @Mapping(target="data", ignore=true)
    Pedido toEntity(PedidoDto dto);

    @Mapping(target="id", ignore=true)
    @Mapping(target="itens", ignore=true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PedidoDto dto,
        @MappingTarget Pedido pedido);

    @Mapping(target="seq", source="id.seq")
    @Mapping(target="produtoId", source="produto.id")
    @Mapping(target="nome", source="produto.nome")
    ItemDto toDto(Item item);

    @Mapping(target="id", ignore=true)
    @Mapping(target="pedido", ignore=true)
    @Mapping(target="produto", ignore=true)
    Item toEntity(ItemDto dto);

    default void updateFromDto(ItemDto dto, @MappingTarget Item item,
        ProdutoRepository produtoRepository)
    {
        if (dto.produtoId() != null) {
            Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto Inexistente"));
            item.setProduto(produto);
        }
        if (dto.qtd() != null)
            item.setQtd(dto.qtd());
    }

    default List<Item> toEntityList(List<ItemDto> dto, Pedido pedido,
        ProdutoRepository produtoRepository)
    {
        int contador = 1;
        List<Item> lista = new ArrayList<>();
        for (ItemDto itemDto : dto) {
            Item item = toEntity(itemDto);
            Produto produto = produtoRepository.findById(itemDto.produtoId()).orElse(null);
            if (produto == null) throw new EntityNotFoundException("Produto inexistente");
            item.setProduto(produto);
            item.setPedido(pedido);
            ItemId itemId = new ItemId(pedido.getId(), contador++);
            item.setId(itemId);
            lista.add(item);
        }
        return lista;
    }
}
