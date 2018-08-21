import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaptainsLog } from 'app/shared/model/captains-log.model';

@Component({
    selector: 'jhi-captains-log-detail',
    templateUrl: './captains-log-detail.component.html'
})
export class CaptainsLogDetailComponent implements OnInit {
    captainsLog: ICaptainsLog;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ captainsLog }) => {
            this.captainsLog = captainsLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
