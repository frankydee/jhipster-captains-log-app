import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IELR } from 'app/shared/model/elr.model';

@Component({
    selector: 'jhi-elr-detail',
    templateUrl: './elr-detail.component.html'
})
export class ELRDetailComponent implements OnInit {
    eLR: IELR;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eLR }) => {
            this.eLR = eLR;
        });
    }

    previousState() {
        window.history.back();
    }
}
