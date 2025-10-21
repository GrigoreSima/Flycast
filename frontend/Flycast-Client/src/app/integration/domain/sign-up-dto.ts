import {ClientDTO} from './client-dto';

export interface SignUpDTO extends ClientDTO{
  password: string;
}
