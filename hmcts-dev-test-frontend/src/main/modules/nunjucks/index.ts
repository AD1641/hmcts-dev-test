import * as path from 'path';
import * as nunjucks from 'nunjucks';
import { Express } from 'express';

export class Nunjucks {
  constructor(private isDev: boolean) {}

  public enableFor(app: Express): void {
    const viewsPath = path.join(__dirname, '..', '..', 'views');

    const govukFrontendPath = path.join(__dirname, '..', '..', '..', '..', 'node_modules', 'govuk-frontend', 'dist');

    console.log('Views path:', viewsPath);
    console.log('GOVUK path:', govukFrontendPath);

    nunjucks.configure([viewsPath, govukFrontendPath], {
      autoescape: true,
      express: app,
      noCache: this.isDev,
    });

    app.set('view engine', 'njk');
  }
}
