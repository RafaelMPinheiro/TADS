export interface Post {
  id: string;
  title: string;
  content: string;
  photo: string;
  userId: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface PaginatedPosts {
  posts: Post[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}
