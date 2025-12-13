import { Product } from '../types/Product';

export const PRODUCTS: Product[] = [
  {
    id: '1',
    name: 'Smartphone Samsung Galaxy A54',
    price: 1299.99,
    originalPrice: 1599.99,
    image: 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=300&h=300&fit=crop',
    description: 'Smartphone com câmera de 50MP, tela de 6.4" Super AMOLED e bateria de longa duração de 5000mAh. Processador Exynos 1380 com 8GB de RAM.',
    rating: 4.5,
    reviews: 2847,
    discount: 19,
    category: 'Eletrônicos'
  },
  {
    id: '2',
    name: 'Fone de Ouvido Bluetooth JBL',
    price: 199.99,
    originalPrice: 299.99,
    image: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop',
    description: 'Fone de ouvido wireless com tecnologia de cancelamento de ruído ativo e até 20h de bateria. Som JBL Pure Bass.',
    rating: 4.3,
    reviews: 1542,
    discount: 33,
    category: 'Áudio'
  },
  {
    id: '3',
    name: 'Camiseta Básica Unissex',
    price: 39.90,
    image: 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=300&h=300&fit=crop',
    description: 'Camiseta 100% algodão penteado, corte moderno e confortável. Disponível em várias cores e tamanhos.',
    rating: 4.0,
    reviews: 856,
    category: 'Roupas'
  },
  {
    id: '4',
    name: 'Notebook Dell Inspiron 15',
    price: 2499.99,
    originalPrice: 2999.99,
    image: 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=300&h=300&fit=crop',
    description: 'Notebook com Intel Core i5, 8GB RAM, SSD 256GB, tela 15.6" Full HD. Ideal para trabalho e estudos.',
    rating: 4.4,
    reviews: 3241,
    discount: 17,
    category: 'Informática'
  },
  {
    id: '5',
    name: 'Relógio Digital Smartwatch',
    price: 299.90,
    originalPrice: 399.90,
    image: 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=300&h=300&fit=crop',
    description: 'Smartwatch com monitor cardíaco, GPS, resistente à água e bateria de até 7 dias. Compatível com iOS e Android.',
    rating: 4.2,
    reviews: 1876,
    discount: 25,
    category: 'Eletrônicos'
  },
  {
    id: '6',
    name: 'Mochila Executiva Premium',
    price: 129.90,
    image: 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=300&h=300&fit=crop',
    description: 'Mochila executiva com compartimento para notebook 15.6", material resistente à água e design elegante.',
    rating: 4.6,
    reviews: 642,
    category: 'Acessórios'
  }
];