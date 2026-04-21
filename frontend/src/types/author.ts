/* 這邊會放前端要用到的對應後端DTO的格式(當然會需要做一些調整、畢竟不是所有資料前端都需要寫)
下面是參考用的程式範例，實際上會根據後端的DTO來調整
// Response
export interface Author {
  id: number;
  name: string;
}

// Request
export interface CreateAuthorRequest {
  name: string;
}
*/
export interface AuthorListItem {
  id: number;
  name: string;
  bookCount: number;
  hasBooks: boolean;
}

export interface AuthorResponse {
  id: number;
  name: string;
}

export interface CreateAuthorRequest {
  name: string;
}


