import apiClient from "./axios";
import type { AuthorResponse } from "../types/author";
import type { AuthorListItem } from "../types/author";

export const getAuthors = () => {
  return apiClient.get<AuthorListItem[]>("/authors")
    .then(response => response.data);
}

export const getAuthorById = (authorid: number) => {
  console.log("API CALLED"); // ⭐
  console.log("baseURL =", apiClient.defaults.baseURL);

  return apiClient.get<AuthorResponse>(`/authors/${authorid}`)
    .then(response => response.data);
};

export const getAuthorByName = (name: string) => {
  return apiClient.get<AuthorResponse>("/authors",
    {params: { name }
  });
};

export const createAuthor = (CreateAuthorRequest: { name: string }) => {
  return apiClient.post("/authors", CreateAuthorRequest);
}
