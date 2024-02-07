import { WebPlugin } from '@capacitor/core';

import {ImageResponseType} from './definitions';
import type { CapMLPlugin, ImageResponseCallback, CallbackID } from './definitions';

export class CapMLWeb extends WebPlugin implements CapMLPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async loadImage(callback: ImageResponseCallback): Promise<CallbackID> {
    console.log('loadImage on js side');
    let type = '';
    const listener = (e: any) => {
      console.log(`imagePickResult ${JSON.stringify(e)}`);
      callback(e);
      type = e.type;
    };
    addEventListener('imagePickResult', listener);
  
    if (type === ImageResponseType.ai) {
      // removeEventListener('imagePickResult', listener);
      return;
    }
  }
}
