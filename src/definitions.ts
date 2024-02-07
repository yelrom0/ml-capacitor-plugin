export interface ImageAIData {
  className: string;
  confidence: number;
}

export interface ImageData {
  name: string;
  data: string;
  mimeType: string;
}

export enum ImageResponseType { 
  image = 'image',
  ai = 'ai'
};

export interface ImageResponse {
  id: string;
  type: ImageResponseType;
  data: ImageData | ImageAIData;
}

export type CallbackID = string | undefined;

export type ImageResponseCallback = (response: ImageResponse) => void;
export interface CapMLPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  loadImage(callback: ImageResponseCallback): Promise<CallbackID>;
}